package vn.webapp.backend.auction.service.auction;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.AuctionRegistrationResponse;
import vn.webapp.backend.auction.dto.AuctionRequest;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.*;
import vn.webapp.backend.auction.repository.*;
import vn.webapp.backend.auction.service.email.EmailService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final JewelryRepository jewelryRepository;
    private final TransactionRepository transactionRepository;
    private final AuctionRegistrationRepository auctionRegistrationRepository;
    private final EmailService emailService;

    @Override
    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(Integer id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
    }

    @Override
    public void deleteAuction(Integer id) {
        Auction auction = getAuctionById(id);
        auction.setState(AuctionState.DELETED);
    }

    @Override
    public List<Auction> findAuctionSortByBetweenStartDayAndEndDay(String startDay, String endDay) {
        LocalDate localDate1 = LocalDate.parse(startDay);
        LocalDate localDate2 = LocalDate.parse(endDay);

        Timestamp timestampStartDate1 = Timestamp.valueOf(localDate1.atStartOfDay());

        LocalDateTime endOfDay = LocalDateTime.of(localDate2, LocalTime.MAX);
        Timestamp timestampEndDate2 = Timestamp.valueOf(endOfDay);


        return auctionRepository.findAuctionSortByBetweenStartdayAndEndday(timestampStartDate1, timestampEndDate2);
    }

    @Override
    public Page<Auction> getByStaffID(Integer id, String auctionName, Pageable pageable) {
        return auctionRepository.findByStaffID(id, auctionName, pageable);
    }

    @Override
    public Auction getCurrentAuctionByJewelryId(Integer id) {
        List<Auction> auctionList = auctionRepository.findAuctionByJewelryId(id);
        if (auctionList.isEmpty()) {
            return null;
        }
        return auctionList.get(0);
    }

    @Override
    public Auction createNewAuction(AuctionRequest request) {
        Optional<User> existStaff = userRepository.findById(request.staffId());
        if (existStaff.isEmpty()) {
            throw new IllegalArgumentException("Staff with ID " + request.staffId() + " not found");
        }

        Optional<Jewelry> existJewelry = jewelryRepository.findById(request.jewelryId());
        if (existJewelry.isEmpty()) {
            throw new IllegalArgumentException("Jewelry with ID " + request.jewelryId() + " not found");
        }
        User staff = existStaff.get();
        Jewelry jewelry = existJewelry.get();
        jewelry.setState(JewelryState.AUCTION);
        Auction auction = new Auction();

        auction.setName(request.name());
        auction.setDescription(request.description());
        auction.setFirstPrice(request.firstPrice());
        auction.setParticipationFee(request.participationFee());
        auction.setDeposit(request.deposit());
        auction.setPriceStep(request.priceStep());
        auction.setCreateDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))));
        auction.setStartDate(request.startDate());
        auction.setEndDate(request.endDate());
        auction.setJewelry(jewelry);
        auction.setState(AuctionState.WAITING);
        auction.setUser(staff);

        auctionRepository.save(auction);
        return auction;
    }

    @Override
    public List<Auction> findAuctionByName(String name) {
        return auctionRepository.findAuctionByNameContaining(name);
    }

    @Override
    public Page<Auction> getAllAuctions(AuctionState state, Pageable pageable, String auctionName, Integer categoryId) {
        return auctionRepository.findByStateAndCategoryNotDeletedOrEmptyState(state, auctionName, pageable, categoryId);
    }

    @Override
    public Auction updateEndTimeAuction(Integer auctionId, Long time) {
        var existingAuction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));

        Timestamp currentEndDate = existingAuction.getEndDate();
        Timestamp newEndDate = new Timestamp(currentEndDate.getTime() + time);

        existingAuction.setEndDate(newEndDate);
        return existingAuction;
    }

    @Override
    public void setAuctionState(Integer id, String state) {
        var existingAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        existingAuction.setState(AuctionState.valueOf(state));
    }

    @Override
    public void deleteAuctionResult(Integer transactionId) throws MessagingException {
        var existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.TRANSACTION_NOT_FOUND));
        Integer auctionId = existingTransaction.getAuction().getId();
        Integer jewelryId = existingTransaction.getAuction().getJewelry().getId();
        Integer userId = existingTransaction.getUser().getId();
        var existingAuction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        var existingJewelry = jewelryRepository.findById(jewelryId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.JEWELRY_NOT_FOUND));
        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        String reason = ReasonMessages.DO_NOT_PAY_ON_TIME;
        existingTransaction.setState(TransactionState.HIDDEN);
        existingAuction.setState(AuctionState.FINISHED);
        existingJewelry.setState(JewelryState.ACTIVE);
        existingUser.setState(AccountState.DISABLE);
        existingUser.setBanReason(reason);

        emailService.sendBlockAccountEmail(
                existingUser.getEmail(),
                existingUser.getFullName(),
                existingUser.getUsername(),
                reason
        );
    }

    @Override
    public List<Auction> getAuctionByState(AuctionState state) {
        return auctionRepository.findByState(state);
    }

    @Override
    public Page<Auction> getAuctionsByStates(List<AuctionState> states, Pageable pageable) {
        return auctionRepository.findByStateIn(states, pageable);
    }

    @Override
    public Page<AuctionRegistrationResponse> getAuctionRegistrations(AuctionState state, String auctionName, Pageable pageable) {
        List<Auction> auctions = auctionRepository.findByState(state, auctionName);
        List<AuctionRegistrationResponse> list = auctions.stream()
                .map(auction -> {
                    Integer numberOfParticipants = auctionRegistrationRepository.countValidParticipantsByAuctionId(auction.getId());
                    return new AuctionRegistrationResponse(
                            auction.getId(),
                            auction.getName(),
                            auction.getStartDate(),
                            auction.getEndDate(),
                            auction.getState(),
                            numberOfParticipants
                    );
                })
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<AuctionRegistrationResponse> pagedAuctions = list.subList(start, end);

        return new PageImpl<>(pagedAuctions, pageable, auctions.size());
    }

    @Override
    public Page<Auction> getAllFailedAuctions(Pageable pageable, String auctionName) {
        return auctionRepository.findAuctionFailedAndName(pageable, auctionName);
    }
}
