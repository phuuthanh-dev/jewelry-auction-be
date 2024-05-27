package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.model.Auction;

import java.sql.Timestamp;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    @Query("SELECT a FROM Auction a WHERE a.startDate >= :startday AND a.endDate <= :endday")
    List<Auction> findAuctionSortByBetweenStartdayAndEndday(@Param("startday") Timestamp startday, @Param("endday") Timestamp endday);

    @Query("SELECT a FROM Auction a WHERE :auctionName = '' OR a.name LIKE %:auctionName%")
    List<Auction> findAuctionByNameContaining(@Param("auctionName") String auctionName);

    List<Auction> findTop3ByStateInOrderByFirstPriceDesc(List<AuctionState> states);

//        Page<Auction> findByState(AuctionState auctionState, Pageable pageable, Integer categoryId);
//    @Query("SELECT a FROM Auction a WHERE (a.state = :auctionState) " +
//            "AND (:categoryId = 0 OR a.jewelry.category.id = :categoryId)")
//    Page<Auction> findByStateAndCategory(
//            @Param("auctionState") AuctionState auctionState,
//            Pageable pageable,
//            @Param("categoryId") Integer categoryId
//    );

    @Query("SELECT a FROM Auction a WHERE a.state = :auctionState")
    List<Auction> findByState(@Param("auctionState") AuctionState auctionState);

    @Query("SELECT a FROM Auction a WHERE " +
            "((:auctionState = 'DELETED' AND a.state != 'DELETED') " +
            "OR (:auctionState != '' AND a.state = :auctionState)) " +
            "AND (:categoryId = 0 OR a.jewelry.category.id = :categoryId) " )
    Page<Auction> findByStateAndCategoryNotDeletedOrEmptyState(
            @Param("auctionState") AuctionState auctionState,
            Pageable pageable,
            @Param("categoryId") Integer categoryId
    );

    @Query("SELECT a FROM Auction a WHERE a.user.id = :staff_id")
    List<Auction> findByStaffID(@Param("staff_id") Integer staff_id);

    Page<Auction> findByStateIn(List<AuctionState> states, Pageable pageable);
}
