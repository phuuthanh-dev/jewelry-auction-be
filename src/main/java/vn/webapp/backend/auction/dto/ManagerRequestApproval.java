package vn.webapp.backend.auction.dto;

import java.sql.Timestamp;

public record ManagerRequestApproval(
        Integer senderId,
        Integer requestApprovalId,
        Timestamp requestTime
) {
}
