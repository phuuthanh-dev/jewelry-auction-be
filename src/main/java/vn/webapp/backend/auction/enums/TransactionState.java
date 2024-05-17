package vn.webapp.backend.auction.enums;

public enum TransactionState {
    SUCCESS("Thành công"),
    FAIL("Không thành công");

    private String vietnameseName;

    TransactionState(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
