package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.BidRequest;
import vn.webapp.backend.auction.enums.AuctionHistoryState;
import vn.webapp.backend.auction.enums.AuctionRegistrationState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.AuctionHistory;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.repository.AuctionHistoryRepository;
import vn.webapp.backend.auction.repository.AuctionRegistrationRepository;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionHistoryServiceImpl implements AuctionHistoryService {

    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionRegistrationRepository auctionRegistrationRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public Page<AuctionHistory> getAuctionHistoryByAuctionId(Pageable pageable, Integer auctionId) {
        var existingAuction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiên đấu giá."));
        return auctionHistoryRepository.findByAuctionId(pageable, existingAuction.getId());
    }

    @Override
    public Page<AuctionHistory> getAuctionHistoryByUsername(Pageable pageable, String username) {
        var existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng."));
        return auctionHistoryRepository.findByUsername(pageable, existingUser.getUsername());
    }

    @Override
    public List<AuctionHistory> getAuctionHistoryByDate(String date) {
        return auctionHistoryRepository.findByDate(date);
    }

    @Override
    public List<AuctionHistory> getAuctionHistoryByAuctionIdWhenFinished(Integer id) {
        return auctionHistoryRepository.findByAuctionIdWhenFinished(id);
    }

    public static String generateBidCode() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }

    @Override
    public void saveBidByUserAndAuction(BidRequest request) {
        var existingUser = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng."));
        var auction = auctionRepository.findById(request.auctionId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiên đấu giá."));

        var auctionHistory = AuctionHistory.builder()
                .user(existingUser)
                .auction(auction)
                .bidCode(generateBidCode())
                .priceGiven(request.priceGiven())
                .state(AuctionHistoryState.ACTIVE)
                .time(request.bidTime())
                .build();

        auction.setLastPrice(request.priceGiven());

        auctionHistoryRepository.save(auctionHistory);
    }

    @Override
    public void deleteBidByUserAndAuction(Integer userId, Integer auctionId) {
        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng."));
        var auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiên đấu giá."));
        var auctionRegistration = auctionRegistrationRepository.findByAuctionIdAndUserIdValid(userId, auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng chưa đăng kí phiên đấu giá."));
        auctionRegistration.setAuctionRegistrationState(AuctionRegistrationState.KICKED_OUT);
        List<AuctionHistory> userBids = auctionHistoryRepository.findByAuctionHistoryByAuctionAndUserActive(userId, auctionId);
        for (AuctionHistory auctionHistory : userBids) {
            auctionHistory.setState(AuctionHistoryState.HIDDEN);
        }
    }
}
