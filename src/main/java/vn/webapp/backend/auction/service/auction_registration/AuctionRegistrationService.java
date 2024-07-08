package vn.webapp.backend.auction.service.auction_registration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.model.AuctionRegistration;

import java.util.List;

public interface AuctionRegistrationService {
    void registerUserForAuction(String username, Integer auctionId);
    List<AuctionRegistration> findByAuctionIdAndValid(Integer auctionId);
    Page<AuctionRegistration> findByUserIdAndValid(Integer userId, String auctionName, Pageable pageable);
}
