package vn.webapp.backend.auction.dto;

public record KickOutResponse (
        Integer userId,
        Double lastPrice,
        String kickReason
){
}
