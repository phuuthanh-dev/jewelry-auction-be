package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.model.JewelryCategory;

public interface JewelryCategoryRepository extends JpaRepository<JewelryCategory, Integer> {
}
