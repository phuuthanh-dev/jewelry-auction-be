package vn.webapp.backend.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.model.AuctionHistory;
import vn.webapp.backend.auction.repository.AuctionHistoryRepository;
import vn.webapp.backend.auction.repository.AuctionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RealTimeService {

    private final AuctionRepository auctionRepository;

    public Double getLastPriceTogether(Integer id) {
        return auctionRepository.findById(id).get().getLastPrice();
    }
}
