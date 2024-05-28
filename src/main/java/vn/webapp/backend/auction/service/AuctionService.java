package vn.webapp.backend.auction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.model.Auction;

import java.sql.Timestamp;
import java.util.List;

public interface AuctionService {
    List<Auction> getAll();
    Auction getAuctionById(Integer id);
    void deleteAuction(Integer id);
    List<Auction> findAuctionByName(String name);
    Page<Auction> getAllAuctions(AuctionState state, Pageable pageable, Integer categoryId);
    void setAuctionState(Integer id, String state);
    Page<Auction> getAuctionsByStates(List<AuctionState> states, Pageable pageable);
    List<Auction> getAuctionByState(AuctionState state);
    List<Auction> findTop3AuctionsByPriceAndState(List<AuctionState> states);
    List<Auction> findAuctionSortByBetweenStartdayAndEndday(String startDay, String endDay);
    List<Auction> getByStaffID(Integer id);
    List<Auction> getAuctionByJewelryId(Integer id);

}
