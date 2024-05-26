package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.model.Bank;
import vn.webapp.backend.auction.repository.BankRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    @Override
    public List<Bank> getAll() {
        return this.bankRepository.findAll();
    }
}
