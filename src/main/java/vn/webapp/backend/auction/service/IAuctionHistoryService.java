package vn.webapp.backend.auction.service;

import vn.webapp.backend.auction.model.AuctionHistory;

import java.sql.Date;
import java.util.List;

public interface IAuctionHistoryService {
    List<AuctionHistory> getAuctionHistoryByAuctionId(Integer auctionId);
    List<AuctionHistory> getAuctionHistoryByUsername(String username);
    List<AuctionHistory> getAuctionHistoryByDate(String date);
}
