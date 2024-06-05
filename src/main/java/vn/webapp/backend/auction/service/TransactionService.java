package vn.webapp.backend.auction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.UserTransactionResponse;
import vn.webapp.backend.auction.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAll();

    Transaction getTransactionById(Integer id);

    UserTransactionResponse getTransactionsDashboardByUsername(String username);

    List<Transaction> getTransactionByType(String typename);

    void setTransactionState(Integer id, String state);
    Page<Transaction> getTransactionsByUsername (String username, Pageable pageable);
}
