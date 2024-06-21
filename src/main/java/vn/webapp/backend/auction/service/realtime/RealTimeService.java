package vn.webapp.backend.auction.service.realtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.BidResponse;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.repository.AuctionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealTimeService {

    private final AuctionRepository auctionRepository;

    public BidResponse getLastPriceTogether(Integer id) {
        Optional<Auction> auctionOptional = auctionRepository.findById(id);

        if (auctionOptional.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.AUCTION_NOT_FOUND);
        }

        Auction auction = auctionOptional.get();
        return new BidResponse(auction.getLastPrice(), auction.getId());
    }
}
