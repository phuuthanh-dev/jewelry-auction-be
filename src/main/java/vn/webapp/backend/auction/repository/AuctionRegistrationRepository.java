package vn.webapp.backend.auction.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.AuctionRegistrationState;
import vn.webapp.backend.auction.model.AuctionRegistration;

import java.util.List;
import java.util.Optional;

public interface AuctionRegistrationRepository extends JpaRepository<AuctionRegistration, Integer> {

    @Query("SELECT ar FROM AuctionRegistration ar JOIN FETCH ar.auction a WHERE a.id = :auctionId AND ar.auctionRegistrationState = :state")
    List<AuctionRegistration> findByAuctionIdAndValid(@Param("auctionId") Integer auctionId, @Param("state") AuctionRegistrationState state);

    @Query("SELECT ar FROM AuctionRegistration ar WHERE ar.auction.id = :auctionId AND ar.user.id = :userId AND ar.auctionRegistrationState = 'VALID'")
    Optional<AuctionRegistration> findByAuctionIdAndUserIdValid(@Param("userId") Integer userId, @Param("auctionId") Integer auctionId);
}
