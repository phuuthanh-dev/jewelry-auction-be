package vn.webapp.backend.auction.dto;

public record BidResponse (
        Double lastPrice,
        Integer auctionId
) {
}
