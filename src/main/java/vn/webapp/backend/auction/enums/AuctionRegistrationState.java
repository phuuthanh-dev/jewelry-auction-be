package vn.webapp.backend.auction.enums;

public enum AuctionRegistrationState {
    VALID("Được phép tham gia"),
    KICKED_OUT("Bị loại khỏi đấu giá");

    private String vietnameseName;

    AuctionRegistrationState(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}

