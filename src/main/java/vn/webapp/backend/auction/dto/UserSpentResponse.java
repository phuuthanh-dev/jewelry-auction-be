package vn.webapp.backend.auction.dto;

public record UserSpentResponse (
        Integer id,
        String avatar,
        String username,
        String fullName,
        String email,
        String phone,
        Double totalSpent
) {
}
