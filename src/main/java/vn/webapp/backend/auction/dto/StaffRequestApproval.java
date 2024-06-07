package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record StaffRequestApproval(
        Integer senderId,
        Integer requestApprovalId,
        Double valuation,
        Timestamp requestTime
) {
}
