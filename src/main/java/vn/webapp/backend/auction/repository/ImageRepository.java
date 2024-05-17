package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.model.Image;


public interface ImageRepository extends JpaRepository<Image, Integer> {
}
