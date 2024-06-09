package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.model.JewelryCategory;

import java.util.Optional;

public interface JewelryCategoryRepository extends JpaRepository<JewelryCategory, Integer> {
    public Optional<JewelryCategory> findByName(String name);
}
