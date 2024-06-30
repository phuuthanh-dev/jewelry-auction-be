package vn.webapp.backend.auction.service.auction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.AuctionRequest;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.JewelryRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService{

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final JewelryRepository jewelryRepository;

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
    public List<Auction> findTop3AuctionsByPriceAndState(List<AuctionState> states) {
        return auctionRepository.findTop3ByStateInOrderByFirstPriceDesc(states);
    }

    @Override
    public List<Auction> findAuctionSortByBetweenStartdayAndEndday(String startDay, String endDay) {
        LocalDate localDate1 = LocalDate.parse(startDay);
        LocalDate localDate2 = LocalDate.parse(endDay);

        Timestamp timestampStartDate1 = Timestamp.valueOf(localDate1.atStartOfDay());

        LocalDateTime endOfDay = LocalDateTime.of(localDate2, LocalTime.MAX);
        Timestamp timestampEndDate2 = Timestamp.valueOf(endOfDay);


        return auctionRepository.findAuctionSortByBetweenStartdayAndEndday(timestampStartDate1, timestampEndDate2);
    }

    @Override
    public Page<Auction> getByStaffID(Integer id, String auctionName, Pageable pageable) {
        return auctionRepository.findByStaffID(id,auctionName, pageable);
    }

    @Override
    public Auction getCurrentAuctionByJewelryId(Integer id) {
        return auctionRepository.findAuctionByJewelryId(id).get(0);
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
    public Page<Auction> getAllAuctions(AuctionState state, Pageable pageable, Integer categoryId) {
        return auctionRepository.findByStateAndCategoryNotDeletedOrEmptyState(state, pageable, categoryId);
    }

    @Override
    public void setAuctionState(Integer id, String state) {
        var existingAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        existingAuction.setState(AuctionState.valueOf(state));
    }

    @Override
    public List<Auction> getAuctionByState(AuctionState state) {
        return auctionRepository.findByState(state);
    }

    @Override
    public Page<Auction> getAuctionsByStates(List<AuctionState> states, Pageable pageable) {
        return auctionRepository.findByStateIn(states, pageable);
    }
}
