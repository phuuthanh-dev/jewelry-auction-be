package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.model.User;

public record UserSpentDTO (
        User user,
        Double totalSpent
) {
}
