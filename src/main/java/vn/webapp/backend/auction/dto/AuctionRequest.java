package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record AuctionRequest(
        String name,
        Timestamp startDate,
        Timestamp endDate,
        String description,
        Double firstPrice,
        Double participationFee,
        Double deposit,
        Double priceStep,
        Integer jewelryId,
        Integer staffId
) {
}
