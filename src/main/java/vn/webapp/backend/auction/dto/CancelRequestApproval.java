package vn.webapp.backend.auction.dto;

public record CancelRequestApproval(
        Integer requestId,
        String note
) {
}
