package vn.webapp.backend.auction.service.bank;

import vn.webapp.backend.auction.model.Bank;

import java.util.List;
public interface BankService {
    List<Bank> getAll();
    Bank getById(Integer id);
}
