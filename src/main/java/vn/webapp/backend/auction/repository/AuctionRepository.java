package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.Jewelry;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
}
