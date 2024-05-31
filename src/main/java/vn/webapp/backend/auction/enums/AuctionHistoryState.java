package vn.webapp.backend.auction.enums;

public enum AuctionHistoryState {
    ACTIVE("Đang hiển thị"),
    HIDDEN("Đã bị ẩn");

    private String vietnameseName;

    AuctionHistoryState(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
