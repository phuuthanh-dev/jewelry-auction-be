package vn.webapp.backend.auction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.model.AuctionHistory;

import java.sql.Date;
import java.util.List;

public interface AuctionHistoryService {
    Page<AuctionHistory> getAuctionHistoryByAuctionId(Pageable pageable, Integer auctionId);
    Page<AuctionHistory> getAuctionHistoryByUsername(Pageable pageable, String username);
    List<AuctionHistory> getAuctionHistoryByDate(String date);
    List<AuctionHistory> getAuctionHistoryByAuctionIdWhenFinished(Integer id);
}
