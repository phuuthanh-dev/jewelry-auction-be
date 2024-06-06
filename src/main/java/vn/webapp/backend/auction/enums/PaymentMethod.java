package vn.webapp.backend.auction.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    COD("Tiền mặt"),
    BANKING("Chuyển khoản");

    private String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

}
