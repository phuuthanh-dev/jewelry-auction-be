package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.user.username = :username")
    List<Transaction> findTransactionsByUsername(@Param("username") String username);
    @Query("SELECT t FROM Transaction t WHERE t.type.name = :typename")
    List<Transaction> findTransactionByType(@Param("typename") String typename);
}
