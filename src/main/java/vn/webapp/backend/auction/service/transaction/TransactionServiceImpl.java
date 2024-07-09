package vn.webapp.backend.auction.service.transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.UserTransactionResponse;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.enums.PaymentMethod;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.*;
import vn.webapp.backend.auction.repository.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Integer numberRegistration = transactionRepository.getCountTransactionsRegistrationByUsername(username);
        Double totalPriceJewelryWonByUsername = transactionRepository.getTotalPriceJewelryWonByUsername(username);
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
    public Page<Transaction> getTransactionsByUsername(String username, String assetName, Pageable pageable) {
        return transactionRepository.findTransactionsByUsername(username,assetName, pageable);
    }

    @Override
    public Page<Transaction> getTransactionByTypeAndState(TransactionType typename, String userName, TransactionState state, Pageable pageable) {
        Page<Transaction> transactionsList = transactionRepository.findTransactionByTypeAndState(typename,userName, state, pageable);
        if (transactionsList.isEmpty()) {
            throw new ResourceNotFoundException("Type'" + typename + "' does not have any transaction items.");
        }
        return transactionsList;
    }

    @Override
    public Page<Transaction> getTransactionHandover(TransactionType typename, String jewelryName, Pageable pageable) {
        return transactionRepository.findTransactionHandover(typename,jewelryName, pageable);
    }

    @Override
    @Transactional
    public void setTransactionState(Integer id, String state) {
        var existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        existingTransaction.setState(TransactionState.valueOf(state));
    }

    @Override
    @Transactional
    public void setTransactionMethod(Integer id, String method) {
        var existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        existingTransaction.setPaymentMethod(PaymentMethod.valueOf(method));
    }

    @Override
    @Transactional
    public void setTransactionAfterPaySuccess(Integer transactionId) {
        var existingAuction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        existingAuction.setState(TransactionState.SUCCEED);
        existingAuction.setPaymentMethod(PaymentMethod.BANKING);
        existingAuction.setPaymentTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))));
    }

    @Override
    public User createTransactionForWinner(Integer auctionId) {
        var auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));

        var userWin = userRepository.findLatestUserInAuctionHistoryByAuctionId(auction.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_WINNER_NOT_FOUND));

        if (!hasTransactionForAuctionAndUser(auctionId, userWin.getId())) {
            Transaction winnerTransaction = Transaction.builder()
                    .user(userWin)
                    .auction(auction)
                    .state(TransactionState.PENDING)
                    .totalPrice(auction.getLastPrice())
                    .feesIncurred(0.0)
                    .createDate(auction.getEndDate())
                    .type(TransactionType.PAYMENT_TO_WINNER)
                    .build();

            transactionRepository.save(winnerTransaction);
        }

        var otherUsers = userRepository.findUsersInAuctionHistoryByAuctionIdExceptWinner(auction.getId(), userWin.getId());
        for (var user : otherUsers) {
            if (!hasTransactionForAuctionAndUser(auctionId, user.getId())) {
                Transaction refundTransaction = Transaction.builder()
                        .user(user)
                        .auction(auction)
                        .state(TransactionState.PENDING)
                        .totalPrice(auction.getDeposit())
                        .feesIncurred(0.0)
                        .createDate(auction.getEndDate())
                        .type(TransactionType.REFUND)
                        .build();

                try {
                    transactionRepository.save(refundTransaction);
                } catch (Exception e) {
                    System.out.println("Error creating refund transaction for user: " + user.getUsername() + " in auction: " + auctionId + ". Error: " + e.getMessage());
                }
            }
        }
        return userWin;
    }

    public boolean hasTransactionForAuctionAndUser(Integer auctionId, Integer userId) {
        Optional<Transaction> transactionOpt = transactionRepository.findTransactionByAuctionIdAndUserId(auctionId, userId);
        return transactionOpt.isPresent();
    }

    @Override
    public List<Transaction> createTransactionForWinnerIfNotExists(Integer userId) {
        List<AuctionRegistration> validRegistrations = auctionRegistrationRepository.findByUserIdValid(userId);
        List<Transaction> createdTransactions = new ArrayList<>();

        if (validRegistrations.isEmpty()) {
            return createdTransactions;
        }

        for (AuctionRegistration registration : validRegistrations) {
            Auction auction = registration.getAuction();

            if (!auction.getState().equals(AuctionState.FINISHED)) {
                continue;
            }

            Optional<User> userWin = userRepository.findLatestUserInAuctionHistoryByAuctionId(registration.getAuction().getId());
            if (userWin.isPresent() && userWin.get().getId() == userId) {
                boolean transactionExists = hasTransactionForAuctionAndUser(registration.getAuction().getId(), userId);

                if (!transactionExists) {
                    Transaction transaction = Transaction.builder()
                            .user(userWin.get())
                            .auction(registration.getAuction())
                            .state(TransactionState.PENDING)
                            .totalPrice(registration.getAuction().getLastPrice())
                            .feesIncurred(0.0)
                            .createDate(registration.getAuction().getEndDate())
                            .type(TransactionType.PAYMENT_TO_WINNER)
                            .build();

                    Transaction createdTransaction = transactionRepository.save(transaction);
                    createdTransactions.add(createdTransaction);
                }
            }
        }
        return createdTransactions;
    }

    @Override
    public Page<Transaction> getOverdueTransactions(String userName,Pageable pageable) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return transactionRepository.findOverdueTransactions(userName,sevenDaysAgo, pageable);
    }
}
