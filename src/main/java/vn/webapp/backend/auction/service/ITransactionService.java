package vn.webapp.backend.auction.service;

import vn.webapp.backend.auction.model.Transaction;

import java.util.List;

public interface ITransactionService {
    List<Transaction> getAll();

    Transaction getTransactionById(Integer id);

    List<Transaction> getTransactionsByUsername(String username);

    List<Transaction> getTransactionByType(String typename);

    void setTransactionState(Integer id, String state);
}
