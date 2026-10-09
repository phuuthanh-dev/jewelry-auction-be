package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>  {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String usename);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllByRole(@Param("role") Role role);

    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.username = :username")
    void updateAvatar(String username, String avatar);

    @Query("SELECT u FROM User u " + "WHERE (:fullName IS NULL OR CONCAT(u.firstName,' ',u.lastName) " +
            "LIKE %:fullName%) " + "AND (:role IS NULL OR u.role = :role) " +
            "AND (:state IS NULL OR u.state = :state)")
    Page<User> findByFullNameContainingAndRoleAndState(@Param("fullName") String fullName, @Param("role") Role role, @Param("state") AccountState state, Pageable pageable);

    @Query("SELECT u FROM User u " + "WHERE (:fullName IS NULL OR CONCAT(u.firstName,' ',u.lastName) LIKE %:fullName%) " + "AND (:role IS NULL OR u.role = :role) " + "AND (:state IS NULL OR u.state <> :state)")
    Page<User> findByFullNameContainingAndRoleAndNotState(@Param("fullName") String fullName, @Param("role") Role role, @Param("state") AccountState state, Pageable pageable);

    @Query("SELECT u FROM User u " +
            "WHERE (:fullName IS NULL OR CONCAT(u.firstName, ' ', u.lastName) LIKE %:fullName%) " +
            "AND (:state IS NULL OR u.state <> :state AND u.state <> 'DISABLE') AND u.cccdFirst IS NOT NULL AND u.cccdLast IS NOT NULL")
    Page<User> findByFullNameContainingAndStateNot(@Param("fullName") String fullName, @Param("state") AccountState state, Pageable pageable);

    @Query("SELECT ah.user " +
            "FROM AuctionHistory ah " +
            "WHERE ah.auction.id = :auctionId " +
            "AND ah.time = (SELECT MAX(ah2.time) FROM AuctionHistory ah2 WHERE ah2.auction.id = :auctionId AND ah2.state='ACTIVE')")
    Optional<User> findLatestUserInAuctionHistoryByAuctionId(@Param("auctionId") Integer auctionId);

    @Query("SELECT a.jewelry.user " +
            "FROM Auction a " +
            "WHERE a.id = :auctionId ")
    Optional<User> findOwnerByAuctionId(@Param("auctionId") Integer auctionId);

    @Query("SELECT u FROM User u JOIN AuctionRegistration ar ON u.id = ar.user.id WHERE ar.auction.id = :auctionId AND u.id <> :winnerId AND ar.auctionRegistrationState = 'VALID'")
    List<User> findUsersInAuctionHistoryByAuctionIdExceptWinner(@Param("auctionId") Integer auctionId, @Param("winnerId") Integer winnerId);

    @Query("SELECT COUNT(u) FROM User u WHERE u.state != 'DISABLE'")
    Integer getTotalUser();

    @Query("SELECT COUNT(u) FROM User u WHERE u.state = :state")
    Integer getTotalUserByState(@Param("state") AccountState state);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Integer getTotalUserByRole(@Param("role") Role role);

    @Query("SELECT COUNT(u) FROM User u WHERE MONTH(u.registerDate) = :month AND YEAR(u.registerDate) = :year")
    Integer getTotalUserByMonthAndYear(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT ar.user FROM AuctionRegistration ar WHERE ar.auction.id = :auctionId AND ar.auctionRegistrationState = 'VALID'")
    List<User> findByAuctionIdAndUserIdValid(@Param("auctionId") Integer auctionId);

    @Query("SELECT t.user FROM Transaction t " +
            "WHERE t.type = vn.webapp.backend.auction.enums.TransactionType.PAYMENT_TO_WINNER " +
            "AND t.state = vn.webapp.backend.auction.enums.TransactionState.SUCCEED " +
            "GROUP BY t.user ORDER BY SUM(t.totalPrice) DESC")
    List<User> findTopUsersByTotalSpent(Pageable pageable);
}
