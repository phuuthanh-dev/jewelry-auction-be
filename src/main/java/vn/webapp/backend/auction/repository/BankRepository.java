package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer> {
}
