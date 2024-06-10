package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.enums.Role;

public record RegisterAccountRequest(
        String firstName,
        String lastName,
        String username,
        String password,
        String email,
        String address,
        String city,
        String district,
        String ward,
        String yob,
        String phone,
        String CCCD,
        Role role,
        String bankAccountName,
        String bankAccountNumber,
        Integer bankId) {
}
