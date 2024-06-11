package vn.webapp.backend.auction.enums;

public enum TransactionState {
    SUCCEED("Thanh toán thành công"),
    PENDING("Chưa thanh toán..."),
    FAILED("Hủy thanh toán");

    private String vietnameseName;

    TransactionState(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
