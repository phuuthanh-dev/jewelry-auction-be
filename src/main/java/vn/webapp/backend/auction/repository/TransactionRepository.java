package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.user.username = :username")
    Page<Transaction> findTransactionsByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.type = :typename AND t.state=:state")
    Page<Transaction> findTransactionByTypeAndState(@Param("typename") TransactionType typename, @Param("state") TransactionState state, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.paymentMethod IS NOT NULL AND t.type = :typename AND t.auction.jewelry.state = 'AUCTION' AND t.auction.jewelry.isHolding= true")
    Page<Transaction> findTransactionHandover(@Param("typename") TransactionType typename , Pageable pageable);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.type = 'REGISTRATION' AND t.user.username = :username")
    Integer getCountTransactionsRegistrationByUsername(@Param("username") String username);

    @Query("SELECT SUM(t.totalPrice * 0.08) FROM Transaction t WHERE t.type = 'PAYMENT_TO_WINNER' AND t.state = 'SUCCEED'")
    Double getTotalCommissionRevenue();

    @Query("SELECT SUM(t.auction.participationFee) FROM Transaction t WHERE t.type = 'REGISTRATION' AND t.state = 'SUCCEED'")
    Double getTotalRegistrationFeeRevenue();

    @Query("SELECT COALESCE(SUM(t.totalPrice * 0.08), 0) " +
            "FROM Transaction t " +
            "WHERE t.type = 'PAYMENT_TO_WINNER' " +
            "AND t.state = 'SUCCEED' " +
            "AND YEAR(t.paymentTime) = :year " +
            "AND MONTH(t.paymentTime) = :month")
    Double getTotalRevenueByMonthAndYear(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT SUM(t.auction.participationFee) " +
            "FROM Transaction t " +
            "WHERE t.type = 'REGISTRATION' " +
            "AND t.state = 'SUCCEED' " +
            "AND YEAR(t.paymentTime) = :year " +
            "AND MONTH(t.paymentTime) = :month")
    Double getTotalRegistrationFeeRevenueByMonthAndYear(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT COALESCE(SUM(t.totalPrice * 0.08), 0) " +
            "FROM Transaction t " +
            "WHERE t.type = 'PAYMENT_TO_WINNER' " +
            "AND t.state = 'SUCCEED' " +
            "AND t.paymentTime >= :startOfDay " +
            "AND t.paymentTime < :startOfNextDay")
    Double getTotalRevenueToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("startOfNextDay") LocalDateTime startOfNextDay);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.type = 'PAYMENT_TO_WINNER' " +
            "AND t.state != 'SUCCEED' " +
            "AND t.state != 'FAILED' " +
            "AND t.createDate < :threeDaysAgo")
    Page<Transaction> findOverdueTransactions(@Param("threeDaysAgo") LocalDateTime threeDaysAgo, Pageable pageable);

    @Query("SELECT SUM(t.totalPrice) FROM Transaction t WHERE t.type = 'PAYMENT_TO_WINNER' AND t.user.username = :username")
    Double getTotalPriceJewelryWonByUsername(@Param("username") String username);

    @Query("SELECT SUM(t.totalPrice) FROM Transaction t WHERE t.type = 'PAYMENT_TO_WINNER' AND t.user.username = :username AND t.state = 'SUCCEED'")
    Double getTotalPriceJewelryWonByUsernameAndAlreadyPay(@Param("username") String username);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.type = 'PAYMENT_TO_WINNER' AND t.user.username = :username")
    Integer getTotalJewelryWon(@Param("username") String username);

    @Query("SELECT t FROM Transaction t WHERE t.type = 'PAYMENT_TO_WINNER' AND t.auction.id = :auctionId AND t.user.id = :userId AND t.state != 'FAILED'")
    Optional<Transaction> findTransactionByAuctionIdAndUserId(@Param("auctionId") Integer auctionId, @Param("userId") Integer userId);
}
