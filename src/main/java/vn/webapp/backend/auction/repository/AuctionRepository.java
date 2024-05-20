package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.Jewelry;

import java.sql.Timestamp;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    @Query("SELECT a FROM Auction a WHERE a.startDate >= :startday AND a.endDate <= :endday")
    List<Auction> findAuctionSortByBetweenStartdayAndEndday(@Param("startday") Timestamp startday, @Param("endday") Timestamp endday);

    List<Auction> findAuctionByNameContaining(String auctionName);


    Page<Auction> findByStateNot(AuctionState auctionState, Pageable pageable);
}
