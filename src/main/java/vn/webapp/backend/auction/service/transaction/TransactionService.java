package vn.webapp.backend.auction.service.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.UserTransactionResponse;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.model.User;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAll();

    Transaction getTransactionById(Integer id);

    UserTransactionResponse getTransactionsDashboardByUsername(String username);

    void setTransactionState(Integer id, String state);

    void setTransactionMethod(Integer id, String method);

    Page<Transaction> getTransactionsByUsername (String username, Pageable pageable);

    User createTransactionForWinner(Integer auctionId);

    Page<Transaction> getTransactionByTypeAndState (TransactionType typename, TransactionState state, Pageable pageable);

    Page<Transaction> getTransactionHandover (TransactionType typename, Pageable pageable);

    List<Transaction> createTransactionForWinnerIfNotExists(Integer userId);

    Page<Transaction> getOverdueTransactions(Pageable pageable);
}
