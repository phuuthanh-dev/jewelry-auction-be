package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.model.Jewelry;

import java.util.List;
import java.util.Optional;

public interface JewelryRepository extends JpaRepository<Jewelry, Integer> {
    @Query("SELECT j FROM Jewelry j WHERE j.user.username = :username")
    List<Jewelry> findJewelryByUsername(@Param("username") String username);

    List<Jewelry> findJewelryByCategoryId(Integer categoryId);

    List<Jewelry> getJewelriesByNameContaining(String name);

    Page<Jewelry> findByState(JewelryState jewelryState, Pageable pageable);

    @Query("SELECT j FROM Jewelry j INNER JOIN RequestApproval r ON j.id = r.jewelry.id WHERE r.sender.role = 'MEMBER' AND r.isConfirm = false AND r.state = 'ACTIVE'")
    Page<Jewelry> findJewelryInWaitlist(Pageable pageable);

    @Query("SELECT j FROM Jewelry j INNER JOIN Auction a ON j.id = a.jewelry.id WHERE a.state = 'FINISHED'")
    Page<Jewelry> findJewelryInHandOver(Pageable pageable);

    @Query("SELECT j FROM Jewelry j WHERE j.state = :state AND j.isHolding = :isHolding AND (:jewelryName IS NULL OR j.name LIKE %:jewelryName%)")
    Page<Jewelry> findJewelryByStateAndIsHolding(@Param("state") JewelryState state, @Param("isHolding") Boolean isHolding, @Param("jewelryName") String jewelryName, Pageable pageable);

    Page<Jewelry> findByUserUsername(String username, Pageable pageable);

    @Query("SELECT j FROM Jewelry j ORDER BY j.id DESC")
    List<Jewelry> findLatestJewelry();

    @Query("SELECT COUNT(j) FROM Jewelry j WHERE j.state = :state")
    Integer countAllJewelriesByState(@Param("state") JewelryState state);

    @Query("SELECT j FROM Jewelry j WHERE j.user.id = :userId AND (:jewelryName IS NULL OR j.name LIKE %:jewelryName%) AND (j.state = 'ACTIVE' OR j.state = 'AUCTION')")
    Page<Jewelry> findJewelryActiveByUserId(@Param("userId") Integer userId,@Param("jewelryName") String jewelryName, Pageable pageable);

}
