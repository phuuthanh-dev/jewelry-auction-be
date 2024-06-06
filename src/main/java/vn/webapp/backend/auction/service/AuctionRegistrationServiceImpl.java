package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.AuctionRegistrationState;
import vn.webapp.backend.auction.enums.PaymentMethod;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.AuctionRegistrationRepository;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.TransactionRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionRegistrationServiceImpl implements AuctionRegistrationService {

    private final AuctionRegistrationRepository auctionRegistrationRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void registerUserForAuction(String username, Integer auctionId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new ResourceNotFoundException("Auction not found"));

        double registrationFee = auction.getParticipationFee() + auction.getDeposit();

        Transaction transaction = Transaction.builder()
                .createDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .totalPrice(registrationFee)
                .feesIncurred(0.0)
                .state(TransactionState.SUCCEED)
                .auction(auction)
                .user(user)
                .type(TransactionType.REGISTRATION)
                .paymentMethod(PaymentMethod.BANKING)
                .build();

        // Save the transaction to the database
        transactionRepository.save(transaction);
        AuctionRegistration registration = AuctionRegistration.builder()
                .user(user)
                .auction(auction)
                .auctionRegistrationState(AuctionRegistrationState.VALID)
                .registrationDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))))
                .registrationFee(registrationFee)
                .transaction(transaction)
                .build();

        // Save the registration to the database
        auctionRegistrationRepository.save(registration);
    }


    @Override
    public List<AuctionRegistration> findByAuctionIdAndValid(Integer auctionId) {
        var auction = auctionRepository.findById(auctionId).orElseThrow(() -> new ResourceNotFoundException("Auction not found"));
        return auctionRegistrationRepository.findByAuctionIdAndValid(auction.getId(), AuctionRegistrationState.VALID);
    }

}
