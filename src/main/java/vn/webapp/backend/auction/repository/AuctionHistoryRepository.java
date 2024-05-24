package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.model.AuctionHistory;

import java.sql.Date;
import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Integer> {
    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.auction.id = :id")
    Page<AuctionHistory> findByAuctionId(Pageable pageable,  @Param("id") Integer id);

    @Query("SELECT ah FROM AuctionHistory ah WHERE ah.user.username = :username")
    Page<AuctionHistory> findByUsername(Pageable pageable, @Param("username") String username);

    @Query(value = "SELECT * FROM auction_history ah WHERE CAST(ah.time AS DATE) = :date", nativeQuery = true)
    List<AuctionHistory> findByDate(@Param("date") String date);
}
