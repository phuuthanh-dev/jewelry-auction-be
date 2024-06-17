package vn.webapp.backend.auction.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    PAY_AT_COUNTER("Thanh toán tại quầy"),
    BANKING("Chuyển khoản");

    private String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

}
