package vn.webapp.backend.auction.enums;

public enum JewelryState {
    ACTIVE("Đang hiển thị"),
    HIDDEN("Đã bị ẩn");

    private String displayName;

    JewelryState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

