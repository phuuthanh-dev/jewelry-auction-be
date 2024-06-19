package vn.webapp.backend.auction.service.transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.UserTransactionResponse;
import vn.webapp.backend.auction.enums.PaymentMethod;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.*;
import vn.webapp.backend.auction.repository.*;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final AuctionRegistrationRepository auctionRegistrationRepository;

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Integer id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.TRANSACTION_NOT_FOUND));
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
    public Page<Transaction> getTransactionByTypeAndState(TransactionType typename, TransactionState state, Pageable pageable) {
        Page<Transaction> transactionsList = transactionRepository.findTransactionByTypeAndState(typename, state, pageable);
        if (transactionsList.isEmpty()) {
            throw new ResourceNotFoundException("Type'" + typename + "' does not have any transaction items.");
        }
        return transactionsList;
    }

    @Override
    public Page<Transaction> getTransactionHandover(TransactionType typename, Pageable pageable) {
        return transactionRepository.findTransactionHandover(typename, pageable);
    }

    @Override
    public void setTransactionState(Integer id, String state) {
        var existingAuction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        existingAuction.setState(TransactionState.valueOf(state));
    }

    @Override
    public void setTransactionMethod(Integer id, String method) {
        var existingAuction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        existingAuction.setPaymentMethod(PaymentMethod.valueOf(method));
    }

    @Override
    public User createTransactionForWinner(Integer auctionId) {
        var auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));

        var userWin = userRepository.findLatestUserInAuctionHistoryByAuctionId(auction.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_WINNER_NOT_FOUND));

        if (hasTransactionForAuctionAndUser(auctionId, userWin.getId())) {
            throw new ResourceNotFoundException(ErrorMessages.TRANSACTION_ALREADY_EXISTS);
        }

        Transaction transaction = Transaction.builder()
                .user(userWin)
                .auction(auction)
                .state(TransactionState.PENDING)
                .totalPrice(auction.getLastPrice())
                .feesIncurred(0.0)
                .createDate(auction.getEndDate())
                .type(TransactionType.PAYMENT_TO_WINNER)
                .build();

        transactionRepository.save(transaction);

        return userWin;
    }

    public boolean hasTransactionForAuctionAndUser(Integer auctionId, Integer userId) {
        Optional<Transaction> transactionOpt = transactionRepository.findTransactionByAuctionIdAndUserId(auctionId, userId);
        return transactionOpt.isPresent();
    }

    @Override
    public void createTransactionForWinnerIfNotExists(Integer userId) {
        List<AuctionRegistration> existingAuctionRegistration = auctionRegistrationRepository.findByUserIdValid(userId);

        if (existingAuctionRegistration.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessages.AUCTION_REGISTRATION_NOT_FOUND);
        }

        for (AuctionRegistration registration : existingAuctionRegistration) {
            Optional<User> userWin = userRepository.findLatestUserInAuctionHistoryByAuctionId(registration.getId());
            if (userWin.isPresent() && userWin.get().getId() == userId) {
                    boolean existTransaction = hasTransactionForAuctionAndUser(registration.getAuction().getId(), userId);
                    if (existTransaction) {
                        return;
                    }
                    Transaction transaction = Transaction.builder()
                            .user(userWin.get())
                            .auction(registration.getAuction())
                            .state(TransactionState.PENDING)
                            .totalPrice(registration.getAuction().getLastPrice())
                            .feesIncurred(0.0)
                            .createDate(registration.getAuction().getEndDate())
                            .type(TransactionType.PAYMENT_TO_WINNER)
                            .build();

                    transactionRepository.save(transaction);
                }

        }
    }
}
