package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record UserRequestApproval(
        Integer senderId,
        Integer jewelryId,
        Timestamp requestTime
) {
}
