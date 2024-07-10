package vn.webapp.backend.auction.dto;

public record BidMessage(
        String username,
        Integer auctionId,
        Long bonusTime
) {
}
