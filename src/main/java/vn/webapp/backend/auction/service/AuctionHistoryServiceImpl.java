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
import vn.webapp.backend.auction.model.ErrorMessages;
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
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        return auctionHistoryRepository.findByAuctionId(pageable, existingAuction.getId());
    }

    @Override
    public Page<AuctionHistory> getAuctionHistoryByUsername(Pageable pageable, String username) {
        var existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
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
    @Transactional
    public void saveBidByUserAndAuction(BidRequest request) {
        var existingUser = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        var auction = auctionRepository.findById(request.auctionId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));

        var auctionHistory = AuctionHistory.builder()
                .user(existingUser)
                .auction(auction)
                .bidCode(generateBidCode())
                .priceGiven(request.priceGiven())
                .state(AuctionHistoryState.ACTIVE)
                .time(request.bidTime())
                .build();

        auctionHistoryRepository.save(auctionHistory);

        // Refresh the auction's last price
        auction = auctionRepository.findById(request.auctionId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));

        if (auction.getLastPrice() == null || request.priceGiven().compareTo(auction.getLastPrice()) > 0) {
            auction.setLastPrice(request.priceGiven());
            auctionRepository.save(auction);
        } else {
            throw new IllegalArgumentException("Giá đấu không hợp lệ.");
        }
    }

    @Override
    public void deleteBidByUserAndAuction(Integer userId, Integer auctionId) {
        var auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        var auctionRegistration = auctionRegistrationRepository.findByAuctionIdAndUserIdValid(userId, auctionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        // KICK KHỎI PHIÊN ĐẤU GIÁ
        auctionRegistration.setAuctionRegistrationState(AuctionRegistrationState.KICKED_OUT);

        // ẨN NHỮNG LẦN ĐẤU GIÁ CỦA USER ĐÓ ĐI
        List<AuctionHistory> userBids = auctionHistoryRepository.findByAuctionHistoryByAuctionAndUserActive(auctionId, userId);
        for (AuctionHistory auctionHistory : userBids) {
            auctionHistory.setState(AuctionHistoryState.HIDDEN);
        }

        // SET LẠI GIÁ CUỐI CỦA PHIÊN
        List<AuctionHistory> lastActiveBids = auctionHistoryRepository.findLastActiveBidByAuctionId(auctionId);
        if (!lastActiveBids.isEmpty()) {
            AuctionHistory lastActiveBid = lastActiveBids.get(0);
            auction.setLastPrice(lastActiveBid.getPriceGiven());
        } else {
            auction.setLastPrice(null);
        }
    }
}
