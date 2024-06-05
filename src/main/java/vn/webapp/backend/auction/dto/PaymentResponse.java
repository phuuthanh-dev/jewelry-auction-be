package vn.webapp.backend.auction.dto;

import lombok.Builder;

public abstract class PaymentResponse {
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}