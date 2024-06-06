package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record BidRequest(
        String username,
        Integer auctionId,
        Double priceGiven,
        Timestamp bidTime
) {
}