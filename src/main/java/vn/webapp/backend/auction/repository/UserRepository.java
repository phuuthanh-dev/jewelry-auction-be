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

    @Query("SELECT u FROM User u " + "WHERE (:fullName IS NULL OR CONCAT(u.firstName,' ',u.lastName) LIKE %:fullName%) " + "AND (:role IS NULL OR u.role = :role) " + "AND (:state IS NULL OR u.state = :state)")
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

    @Query(nativeQuery = true,
            value = "SELECT TOP 5 u.* " +
                    "FROM [Transaction] t " +
                    "JOIN [User] u ON t.user_id = u.id " +
                    "WHERE t.transaction_type = 'PAYMENT_TO_WINNER' " +
                    "      AND t.transaction_state = 'SUCCEED' " +
                    "GROUP BY u.id, u.cccd, u.address, u.avatar, u.bank_account_name, " +
                    "         u.bank_account_number, u.bank_id, u.city, u.district, " +
                    "         u.email, u.first_name, u.last_name, u.phone, u.password, " +
                    "         u.role, u.state, u.register_date, u.username, u.ward, u.year_of_birth, " +
                    "         u.cccd_first, u.cccd_last, u.cccd_from " +
                    "ORDER BY SUM(t.total_price) DESC")
    List<User> findTopUsersByTotalSpent();
}
