package vn.webapp.backend.auction.dto;

public record ResetPasswordRequest(
        String token,
        String password) {
}
