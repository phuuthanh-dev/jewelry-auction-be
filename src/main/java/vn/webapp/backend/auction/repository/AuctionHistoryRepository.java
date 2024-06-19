package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.AuctionHistoryState;
import vn.webapp.backend.auction.model.AuctionHistory;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Integer> {
    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :id AND ah.state = 'ACTIVE'")
    Page<AuctionHistory> findByAuctionId(Pageable pageable, @Param("id") Integer id);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.user.username = :username")
    Page<AuctionHistory> findByUsername(Pageable pageable, @Param("username") String username);

    @Query(value = "SELECT ah FROM auction_history ah WHERE CAST(ah.time AS DATE) = :date", nativeQuery = true)
    List<AuctionHistory> findByDate(@Param("date") String date);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :id")
    List<AuctionHistory> findByAuctionIdWhenFinished(@Param("id") Integer id);

    @Query("SELECT COUNT(ah) FROM AuctionHistory ah WHERE ah.user.username = :username")
    Integer getTotalBidByUsername(@Param("username") String username);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :auctionId AND ah.user.id = :userId AND ah.state = 'ACTIVE'")
    List<AuctionHistory> findByAuctionHistoryByAuctionAndUserActive(@Param("auctionId") Integer auctionId, @Param("userId") Integer userId);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :auctionId AND ah.state != 'HIDDEN' ORDER BY ah.time DESC")
    List<AuctionHistory> findLastActiveBidByAuctionId(@Param("auctionId") Integer auctionId);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :auctionId AND ah.state != 'HIDDEN' ORDER BY ah.priceGiven DESC")
    List<AuctionHistory> findTopBidByAuctionId(@Param("auctionId") Integer auctionId);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :auctionId AND ah.user.id = :userId AND ah.state = :state")
    Page<AuctionHistory> findByAuctionIdAndUserId(@Param("auctionId") Integer auctionId, @Param("userId") Integer userId,@Param("state") AuctionHistoryState state, Pageable pageable);

}
