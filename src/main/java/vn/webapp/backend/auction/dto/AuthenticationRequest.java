package vn.webapp.backend.auction.dto;

public record AuthenticationRequest (
        String username,
        String email,
        String password
) {
}
