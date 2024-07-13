package vn.webapp.backend.auction.dto;

public record KickOutMessage (
        Integer auctionId,
        String username
){
}
