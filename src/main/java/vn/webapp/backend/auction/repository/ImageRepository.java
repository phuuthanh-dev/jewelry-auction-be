package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.model.Image;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImageByJewelryId(Integer jewelryId);
    @Query("SELECT i FROM Image i WHERE i.icon = true and i.jewelry.id = :jewelryId")
    Image findImageByIconAndJewelryId(@Param("jewelryId") Integer jewelryId);
}
