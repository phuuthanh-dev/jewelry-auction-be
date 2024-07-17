package vn.webapp.backend.auction.enums;

public enum JewelryMaterial {
    GOLD("Vàng"),
    SILVER("Bạc"),
    PLATINUM("Bạch kim"),
    DIAMOND("Kim cương");

    private String displayName;

    JewelryMaterial(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
