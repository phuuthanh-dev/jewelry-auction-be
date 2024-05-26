package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.model.AuctionRegistration;

public interface AuctionRegistrationRepository extends JpaRepository<AuctionRegistration, Integer> {
}
