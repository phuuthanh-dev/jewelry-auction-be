package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.AuctionRegistrationState;
import vn.webapp.backend.auction.model.AuctionRegistration;

import java.util.List;

public interface AuctionRegistrationRepository extends JpaRepository<AuctionRegistration, Integer> {

    @Query("SELECT ar FROM AuctionRegistration ar JOIN FETCH ar.auction a WHERE a.id = :auctionId AND ar.state = :state")
    List<AuctionRegistration> findByAuctionIdAndValid(@Param("auctionId") Integer auctionId, @Param("state") AuctionRegistrationState state);
}
