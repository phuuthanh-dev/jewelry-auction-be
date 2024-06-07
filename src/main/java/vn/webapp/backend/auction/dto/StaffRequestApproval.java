package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record StaffRequestApproval(
        Integer senderId,
        Integer requestId,
        Double valuation,
        Timestamp requestTime
) {
}
