package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.model.AuctionHistory;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Integer> {
    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :id")
    List<AuctionHistory> findByAuctionId(@Param("id") Integer id);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.user.username = :username")
    List<AuctionHistory> findByUsername(@Param("username") String username);

}
