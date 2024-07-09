package vn.webapp.backend.auction.service.auction_registration;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.*;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.exception.UserNotAllowedAccess;
import vn.webapp.backend.auction.model.*;
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
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));

        if (user.getState() != AccountState.VERIFIED) {
            throw new UserNotAllowedAccess(ErrorMessages.USER_NOT_VERIFIED);
        }

        double registrationFee = auction.getParticipationFee() + auction.getDeposit();

        Transaction transaction = Transaction.builder()
                .createDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))))
                .paymentTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))))
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
        var auction = auctionRepository.findById(auctionId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.AUCTION_NOT_FOUND));
        return auctionRegistrationRepository.findByAuctionIdAndValid(auction.getId(), AuctionRegistrationState.VALID);
    }

    @Override
    public Page<AuctionRegistration> findByUserIdAndValid(Integer userId,String auctionName, Pageable pageable) {
        return  auctionRegistrationRepository.findByUserIdAndValid(userId,auctionName,pageable);
    }

}
