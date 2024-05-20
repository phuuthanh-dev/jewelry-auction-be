package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.repository.AuctionRepository;

import java.sql.Timestamp;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AuctionService implements IAuctionService{

    private final AuctionRepository auctionRepository;

    @Override
    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(Integer id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phiên đấu giá này không tồn tại"));
    }

    @Override
    public void deleteAuction(Integer id) {
        Auction auction = getAuctionById(id);
        auctionRepository.delete(auction);
    }

    @Override
    public List<Auction> findAuctionSortByBetweenStartdayAndEndday(Timestamp startDay, Timestamp endDay) {
        return auctionRepository.findAuctionSortByBetweenStartdayAndEndday(startDay,endDay);
    }
}
