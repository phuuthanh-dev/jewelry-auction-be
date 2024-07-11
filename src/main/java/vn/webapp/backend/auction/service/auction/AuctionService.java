package vn.webapp.backend.auction.service.auction;

import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.AuctionRegistrationResponse;
import vn.webapp.backend.auction.dto.AuctionRequest;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.model.Auction;

import java.util.List;

public interface AuctionService {
    List<Auction> getAll();

    Auction getAuctionById(Integer id);

    void deleteAuction(Integer id);

    List<Auction> findAuctionByName(String name);

    Page<Auction> getAllAuctions(AuctionState state, Pageable pageable, String auctionName, Integer categoryId);

    void setAuctionState(Integer id, String state);

    Page<Auction> getAuctionsByStates(List<AuctionState> states, Pageable pageable);

    List<Auction> getAuctionByState(AuctionState state);

    List<Auction> findTop3AuctionsByPriceAndState(List<AuctionState> states);

    List<Auction> findAuctionSortByBetweenStartDayAndEndDay(String startDay, String endDay);

    Page<Auction> getByStaffID(Integer id, String auctionName, Pageable pageable);

    Auction getCurrentAuctionByJewelryId(Integer id);

    Auction createNewAuction(AuctionRequest request);

    void deleteAuctionResult(Integer transactionId) throws MessagingException;

    Page<AuctionRegistrationResponse> getAuctionRegistrations(AuctionState state, String auctionName, Pageable pageable);

    Page<Auction> getAllFailedAuctions(Pageable pageable, String auctionName);

    Auction updateEndTimeAuction(Integer auction, Long time);
}
