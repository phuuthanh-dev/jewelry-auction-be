package vn.webapp.backend.auction.enums;

public enum TransactionType {
    REGISTRATION("Đăng kí tham gia phiên"),
    REFUND("Hoàn tiền đăng kí tham gia phiên"),
    PAYMENT_TO_SELLER("Thanh toán cho người gửi sản phẩm đấu giá"),
    PAYMENT_TO_BUYER("Thanh toán cho người mua");

    private String vietnameseName;

    TransactionType(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
