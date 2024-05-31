package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String usename);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllByRole(@Param("role") Role role);

    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.username = :username")
    void updateAvatar(String username, String avatar);

    @Query("SELECT u FROM User u " + "WHERE (:fullName IS NULL OR CONCAT(u.firstName,' ',u.lastName) LIKE %:fullName%) " + "AND (:roleId IS NULL OR u.role = :role) " + "AND (:state IS NULL OR u.state = :state)")
    Page<User> findByFullNameContainingAndRoleAndState(@Param("fullName") String fullName, @Param("roleId") Role role, @Param("state") AccountState state, Pageable pageable);

    Page<User> findByFullNameContainingAndRoleNotAndState(String fullName, Integer roleId, AccountState state, Pageable pageable);

    @Query("SELECT ah.user " +
            "FROM AuctionHistory ah " +
            "WHERE ah.auction.id = :auctionId " +
            "AND ah.time = (SELECT MAX(ah2.time) FROM AuctionHistory ah2 WHERE ah2.auction.id = :auctionId AND ah2.state=ACTIVE)")
    Optional<User> findLatestUserInAuctionHistoryByAuctionId(@Param("auctionId") Integer auctionId);
}
