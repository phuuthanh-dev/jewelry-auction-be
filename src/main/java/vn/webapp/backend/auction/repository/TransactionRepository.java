package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;
import vn.webapp.backend.auction.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.user.username = :username")
    Page<Transaction> findTransactionsByUsername(@Param("username") String username, Pageable pageable);
    @Query("SELECT t FROM Transaction t WHERE t.type = :typename AND t.state=:state")
    Page<Transaction> findTransactionByTypeAndState(@Param("typename") TransactionType typename, @Param("state") TransactionState state, Pageable pageable);
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.type = 'REGISTRATION' AND t.user.username = :username")
    Integer getCountTransactionsRegistrationByUsername(@Param("username") String username);
    @Query("SELECT SUM(t.totalPrice) FROM Transaction t WHERE t.type = 'PAYMENT_TO_BUYER' AND t.user.username = :username")
    Double getTotalPriceJewelryWonByUsername(@Param("username") String username);
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.type = 'PAYMENT_TO_BUYER' AND t.user.username = :username")
    Integer getTotalJewelryWon(@Param("username") String username);
}
