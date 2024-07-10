package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record BidResponse (
        Double lastPrice,
        Integer auctionId,
        Timestamp endDate,
        Long bonusTime,
        String username
) {
}
