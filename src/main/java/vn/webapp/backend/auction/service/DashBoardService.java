package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.BankRepository;
import vn.webapp.backend.auction.repository.JewelryRepository;
import vn.webapp.backend.auction.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class DashBoardService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final JewelryRepository jewelryRepository;


    public DashBoardResponse getInformation(Integer yearGetRegisterAccount, Integer yearGetAuction) {
        Integer totalUser = userRepository.getTotalUser();
        Integer totalAuction = auctionRepository.countAllAuctions();
        Integer totalJewelryActive = jewelryRepository.countAllJewelriesActive();
        Integer totalAuctionFailed = auctionRepository.countAllAuctionsFailed();
        Integer totalAuctionSuccess = auctionRepository.countAllAuctionsSuccessful();

        Integer[] totalUsersRegisterByMonth = new Integer[12];
        Integer[] totalAuctionByMonth = new Integer[12];

        for (int month = 1; month <= 12; month++) {
            totalUsersRegisterByMonth[month - 1] = userRepository.getTotalUserByMonthAndYear(month, yearGetRegisterAccount);
        }

        for (int month = 1; month <= 12; month++) {
            totalAuctionByMonth[month - 1] = auctionRepository.countAuctionsByMonthAndYear(month, yearGetAuction);
        }

        double percentAuctionFailed = (double) totalAuctionFailed / totalAuction * 100;
        double percentAuctionSuccess = (double) totalAuctionSuccess / totalAuction * 100;

        return DashBoardResponse.builder()
                .totalUser(totalUser)
                .totalJewelryActive(totalJewelryActive)
                .totalAuctions(totalAuction)
                .totalUsersByMonth(totalUsersRegisterByMonth)
                .totalAuctionByMonth(totalAuctionByMonth)
                .percentAuctionFailed(percentAuctionFailed)
                .percentAuctionSuccess(percentAuctionSuccess).build();
    }
}
