package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.Jewelry;
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
        auction.setState(AuctionState.DELETED);
    }

    @Override
    public List<Auction> findAuctionSortByBetweenStartdayAndEndday(Timestamp startDay, Timestamp endDay) {
        return auctionRepository.findAuctionSortByBetweenStartdayAndEndday(startDay,endDay);
    }

    @Override
    public List<Auction> findAuctionByName(String name) {
        return auctionRepository.findAuctionByNameContaining(name);
    }

    @Override
    public Page<Auction> getAllAuctions(Pageable pageable) {
        return auctionRepository.findByStateNot(AuctionState.DELETED, pageable);
    }

    @Override
    public void setAuctionState(Integer id, String state) {
        var existingAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiên đấu giá."));
        existingAuction.setState(AuctionState.valueOf(state));
    }
}
