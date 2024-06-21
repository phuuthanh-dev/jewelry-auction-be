package vn.webapp.backend.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardResponse {
    private Integer totalAuctions;
    private Double totalRevenueToday;
    private Integer totalUser;
    private Integer totalJewelryActive;
    private Integer totalJewelryWaitApproving;
    private Integer totalAuctionJewelry;
    private Integer totalUsersActive;
    private Integer totalUsersInActive;
    private Integer totalMembers;
    private Integer totalStaffs;
    private Integer totalManagers;
    private Integer totalAdmins;
    private double percentAuctionFailed;
    private double percentAuctionSuccess;
    private double participationRate;
    private double notParticipationRate;
    private Integer[] totalUsersByMonth;
    private Integer[] totalAuctionByMonth;
    private Double totalRevenue;
    private Double[] totalRevenueByMonth;
}
