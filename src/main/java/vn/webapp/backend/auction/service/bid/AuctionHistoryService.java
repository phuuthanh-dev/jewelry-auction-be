package vn.webapp.backend.auction.service.bid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.BidRequest;
import vn.webapp.backend.auction.enums.AuctionHistoryState;
import vn.webapp.backend.auction.model.AuctionHistory;

import java.util.List;

public interface AuctionHistoryService {
    Page<AuctionHistory> getAuctionHistoryByAuctionId(Pageable pageable, Integer auctionId);
    Page<AuctionHistory> getAuctionHistoryByUsername(Pageable pageable, String username);
    List<AuctionHistory> getAuctionHistoryByDate(String date);
    List<AuctionHistory> getAuctionHistoryByAuctionIdWhenFinished(Integer id);
    void saveBidByUserAndAuction(BidRequest request);
    void deleteBidByUserAndAuction(Integer userId, Integer auctionId);
    Page<AuctionHistory> getAuctionHistoryByAuctionIdAndUserId(Integer auctionId, Integer userId, AuctionHistoryState state, Pageable pageable);
}
