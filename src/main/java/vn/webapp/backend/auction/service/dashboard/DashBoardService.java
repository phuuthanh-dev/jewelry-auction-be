package vn.webapp.backend.auction.service.dashboard;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.repository.*;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class DashBoardService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final JewelryRepository jewelryRepository;
    private final AuctionRegistrationRepository auctionRegistrationRepository;
    private final TransactionRepository transactionRepository;

    public DashBoardResponse getInformation(Integer yearGetRegisterAccount, Integer yearGetAuction, Integer yearGetRevenue) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime startOfNextDay = startOfDay.plusDays(1);

        Integer totalUser = userRepository.getTotalUser();
        Integer totalAuction = auctionRepository.countAllAuctions();
        Integer totalJewelryActive = jewelryRepository.countAllJewelriesActive();
        Integer totalAuctionFailed = auctionRepository.countAllAuctionsFailed();
        Integer totalAuctionSuccess = auctionRepository.countAllAuctionsSuccessful();
        Double totalRegistrationFee = auctionRegistrationRepository.sumTotalRegistrationFee();
        Double totalCommissionRevenue = transactionRepository.getTotalCommissionRevenue();
        Double totalRevenueToday = transactionRepository.getTotalRevenueToday(startOfDay, startOfNextDay);

        Integer[] totalUsersRegisterByMonth = new Integer[12];
        Integer[] totalAuctionByMonth = new Integer[12];
        Double[] totalRevenueByMonth = new Double[12];

        for (int month = 1; month <= 12; month++) {
            totalUsersRegisterByMonth[month - 1] = userRepository.getTotalUserByMonthAndYear(month, yearGetRegisterAccount);
            totalAuctionByMonth[month - 1] = auctionRepository.countAuctionsByMonthAndYear(month, yearGetAuction);
            totalRevenueByMonth[month - 1] = transactionRepository.getTotalRevenueByMonthAndYear(month, yearGetRevenue);
        }

        double percentAuctionFailed = (double) totalAuctionFailed / totalAuction * 100;
        double percentAuctionSuccess = (double) totalAuctionSuccess / totalAuction * 100;

        Double totalRevenue = totalRegistrationFee + totalCommissionRevenue;

        return DashBoardResponse.builder()
                .totalUser(totalUser)
                .totalRevenueToday(totalRevenueToday)
                .totalJewelryActive(totalJewelryActive)
                .totalAuctions(totalAuction)
                .totalUsersByMonth(totalUsersRegisterByMonth)
                .totalAuctionByMonth(totalAuctionByMonth)
                .percentAuctionFailed(percentAuctionFailed)
                .percentAuctionSuccess(percentAuctionSuccess)
                .totalRevenue(totalRevenue)
                .totalRevenueByMonth(totalRevenueByMonth)
                .build();
    }
}
