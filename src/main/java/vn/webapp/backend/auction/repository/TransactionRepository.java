package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
