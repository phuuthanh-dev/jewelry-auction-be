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
    private double percentAuctionFailed;
    private double percentAuctionSuccess;
    private Integer[] totalUsersByMonth;
    private Integer[] totalAuctionByMonth;
    private Double totalRevenue;
    private Double[] totalRevenueByMonth;
}
