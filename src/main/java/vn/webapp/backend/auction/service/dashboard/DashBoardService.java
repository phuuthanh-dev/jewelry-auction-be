package vn.webapp.backend.auction.service.dashboard;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.enums.Role;
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

        // Total counts
        Integer totalUser = userRepository.getTotalUser();
        Integer totalAuction = auctionRepository.countAllAuctions();
        Integer totalActiveJewelry = jewelryRepository.countAllJewelriesByState(JewelryState.ACTIVE);
        Integer totalApprovingJewelry = jewelryRepository.countAllJewelriesByState(JewelryState.APPROVING);
        Integer totalAuctionJewelry = jewelryRepository.countAllJewelriesByState(JewelryState.AUCTION);
        Integer totalAuctionFailed = auctionRepository.countAllAuctionsFailed();
        Integer totalAuctionSuccess = auctionRepository.countAllAuctionsSuccessful();
        Integer totalAuctionsFinished = auctionRepository.countAllAuctionsFinished();
        Integer totalUsersVerified = userRepository.getTotalUserByState(AccountState.VERIFIED);
        Integer totalUsersActive = userRepository.getTotalUserByState(AccountState.ACTIVE);
        Integer totalUsersInActive = userRepository.getTotalUserByState(AccountState.INACTIVE);
        Integer totalMembers = userRepository.getTotalUserByRole(Role.MEMBER);
        Integer totalStaffs = userRepository.getTotalUserByRole(Role.STAFF);
        Integer totalManagers = userRepository.getTotalUserByRole(Role.MANAGER);
        Integer totalAdmins = userRepository.getTotalUserByRole(Role.ADMIN);
        Long totalUsersRegister = auctionRegistrationRepository.countDistinctUsersRegistered();
        Long totalUsersNotRegistered = totalUser - totalUsersRegister;

        // Total revenues
        Double totalCommissionRevenue = transactionRepository.getTotalCommissionRevenue();
        Double totalRevenueToday = transactionRepository.getTotalRevenueToday(startOfDay, startOfNextDay);
        Double totalRegistrationFeeRevenue = transactionRepository.getTotalRegistrationFeeRevenue();

        // Arrays for monthly data
        Integer[] totalUsersRegisterByMonth = new Integer[12];
        Integer[] totalAuctionByMonth = new Integer[12];
        Double[] totalRevenueByMonth = new Double[12];

        // Calculate data for each month of the year
        for (int month = 1; month <= 12; month++) {
            totalUsersRegisterByMonth[month - 1] = userRepository.getTotalUserByMonthAndYear(month, yearGetRegisterAccount);
            totalAuctionByMonth[month - 1] = auctionRepository.countAuctionsByMonthAndYear(month, yearGetAuction);

            Double totalRevenue = transactionRepository.getTotalRevenueByMonthAndYear(month, yearGetRevenue);
            totalRevenue = (totalRevenue != null) ? totalRevenue : 0.0;

            Double totalRegistrationFeeRevenueByMonthAndYear = transactionRepository.getTotalRegistrationFeeRevenueByMonthAndYear(month, yearGetRevenue);
            totalRegistrationFeeRevenueByMonthAndYear = (totalRegistrationFeeRevenueByMonthAndYear != null) ? totalRegistrationFeeRevenueByMonthAndYear : 0.0;

            totalRevenueByMonth[month - 1] = totalRevenue + totalRegistrationFeeRevenueByMonthAndYear;
        }

        double percentAuctionFailed = (double) totalAuctionFailed / totalAuctionsFinished * 100;
        double percentAuctionSuccess = (double) totalAuctionSuccess / totalAuctionsFinished * 100;
        double participationRate = (double) totalUsersRegister / totalUser * 100;
        double notParticipationRate = (double) totalUsersNotRegistered / totalUser * 100;

        Double totalRevenue = totalRegistrationFeeRevenue + totalCommissionRevenue;

        return DashBoardResponse.builder()
                .totalUser(totalUser)
                .totalRevenueToday(totalRevenueToday)
                .totalJewelryActive(totalActiveJewelry)
                .totalJewelryWaitApproving(totalApprovingJewelry)
                .totalAuctionJewelry(totalAuctionJewelry)
                .totalAuctions(totalAuction)
                .totalUsersVerified(totalUsersVerified)
                .totalUsersActive(totalUsersActive)
                .totalUsersInActive(totalUsersInActive)
                .totalMembers(totalMembers)
                .totalStaffs(totalStaffs)
                .totalManagers(totalManagers)
                .totalAdmins(totalAdmins)
                .totalUsersByMonth(totalUsersRegisterByMonth)
                .totalAuctionByMonth(totalAuctionByMonth)
                .percentAuctionFailed(percentAuctionFailed)
                .percentAuctionSuccess(percentAuctionSuccess)
                .notParticipationRate(notParticipationRate)
                .participationRate(participationRate)
                .totalRevenue(totalRevenue)
                .totalRevenueByMonth(totalRevenueByMonth)
                .build();
    }
}
