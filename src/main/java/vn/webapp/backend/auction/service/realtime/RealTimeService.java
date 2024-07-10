package vn.webapp.backend.auction.service.realtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.BidResponse;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.repository.AuctionRepository;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealTimeService {

    private final AuctionRepository auctionRepository;

    public BidResponse getLastPriceTogether(Integer id, Long bonusTime, String username) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.AUCTION_NOT_FOUND));

        Timestamp currentEndDate = auction.getEndDate();
        Timestamp newEndDate = new Timestamp(currentEndDate.getTime() + bonusTime);
        auction.setEndDate(newEndDate);
        auctionRepository.save(auction);

        return new BidResponse(auction.getLastPrice(), auction.getId(), auction.getEndDate(), bonusTime, username);
    }
}
