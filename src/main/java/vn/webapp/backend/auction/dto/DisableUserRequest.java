package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.enums.AccountState;

public record DisableUserRequest(
        AccountState state,
        String reason
) {
}
