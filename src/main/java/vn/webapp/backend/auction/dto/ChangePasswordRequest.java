package vn.webapp.backend.auction.dto;

public record ChangePasswordRequest(
        String token,
        String oldPassword,
        String newPassword) {
}
