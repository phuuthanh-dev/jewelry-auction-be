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
    private Integer totalUser;
    private Integer totalJewelryActive;
    private double percentAuctionFailed;
    private double percentAuctionSuccess;
}
