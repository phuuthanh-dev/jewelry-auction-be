package vn.webapp.backend.auction.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.Bank;

public record RegisterAccountRequest(
        String firstName,
        String lastName,
        String username,
        String password,
        String email,
        String address,
        String province,
        String city,
        String yob,
        String phone,
        String CCCD,
        Role role,
        String bankAccountName,
        String bankAccountNumber,
        Integer bankId) {
}
