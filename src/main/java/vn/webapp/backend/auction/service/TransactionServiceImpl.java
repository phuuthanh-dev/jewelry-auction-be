package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.UserTransactionResponse;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.repository.AuctionHistoryRepository;
import vn.webapp.backend.auction.repository.TransactionRepository;

import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;
    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Integer id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Giao dịch này không tồn tại"));
    }

    @Override
    public UserTransactionResponse getTransactionsDashboardByUsername(String username) {
        Integer numberRegistration =  transactionRepository.getCountTransactionsRegistrationByUsername(username);
        Double totalPriceJewelryWonByUsername =  transactionRepository.getTotalPriceJewelryWonByUsername(username);
        Integer totalJewelryWon = transactionRepository.getTotalJewelryWon(username);
        Integer totalBid = auctionHistoryRepository.getTotalBidByUsername(username);
        return UserTransactionResponse.builder()
                .numberTransactionsRequest(numberRegistration)
                .totalPriceJewelryWonByUsername(totalPriceJewelryWonByUsername)
                .totalJewelryWon(totalJewelryWon)
                .totalBid(totalBid)
                .build();
    }

    @Override
    public Page<Transaction> getTransactionsByUsername (String username, Pageable pageable) {
        return transactionRepository.findTransactionsByUsername(username, pageable);
    }

    @Override
    public List<Transaction> getTransactionByType(String typename) {
        List<Transaction> transactionsList = transactionRepository.findTransactionByType(typename);
        if (transactionsList.isEmpty()) {
            throw new ResourceNotFoundException("Type'" + typename + "' does not have any transaction items.");
        }
        return transactionsList;
    }

    @Override
    public void setTransactionState(Integer id, String state) {
        var existingAuction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiên giao dịch."));
        existingAuction.setState(TransactionState.valueOf(state));
    }
}
