package vn.webapp.backend.auction.enums;

public enum TransactionState {
    SUCCEED("Thành công"),
    FAILED("Không thành công");

    private String vietnameseName;

    TransactionState(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
