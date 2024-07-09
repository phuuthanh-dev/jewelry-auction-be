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
    private Double totalRevenueToday;
    private Integer totalUser;
    private Integer totalJewelryActive;
    private Integer totalJewelryWaitApproving;
    private Integer totalAuctionJewelry;
    private Integer totalUsersVerified;
    private Integer totalUsersActive;
    private Integer totalUsersInActive;
    private Integer totalMembers;
    private Integer totalStaffs;
    private Integer totalManagers;
    private Integer totalAdmins;
    private double auctionFailed;
    private double auctionSuccess;
    private Double[] totalParticipationByMonth;
    private Integer[] totalUsersByMonth;
    private Integer[] totalAuctionByMonth;
    private Double[] totalRevenueNear10Year;
    private Double[] totalRevenueByMonth;
}
