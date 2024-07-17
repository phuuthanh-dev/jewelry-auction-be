package vn.webapp.backend.auction.service.realtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.BidResponse;
import vn.webapp.backend.auction.dto.KickOutResponse;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.AuctionRegistrationRepository;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealTimeService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final AuctionRegistrationRepository auctionRegistrationRepository;

    public BidResponse bidRealtime(Integer id, Long bonusTime, String username) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.AUCTION_NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.USER_NOT_FOUND));

        Timestamp currentEndDate = auction.getEndDate();
        Timestamp newEndDate = new Timestamp(currentEndDate.getTime() + bonusTime);
        auction.setEndDate(newEndDate);
        auctionRepository.save(auction);

        return new BidResponse(auction.getLastPrice(), auction.getJewelry().getBuyNowPrice(), auction.getId(),
                auction.getEndDate(), bonusTime, user.getUsername());
    }

    public KickOutResponse staffKickOutMemberRealtime(Integer id, String username) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.AUCTION_NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.USER_NOT_FOUND));
        AuctionRegistration auctionRegistration = auctionRegistrationRepository.findByAuctionIdAndUserIdInValid(user.getId(), auction.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.AUCTION_REGISTRATION_NOT_FOUND));
        return new KickOutResponse(auctionRegistration.getUser().getId(), auction.getLastPrice(), auctionRegistration.getKickReason());
    }
}
