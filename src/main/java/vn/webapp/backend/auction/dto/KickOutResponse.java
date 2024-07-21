package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record KickOutResponse (
        Integer userId,
        Double lastPrice,
        String kickReason,
        Timestamp endDate
){
}
