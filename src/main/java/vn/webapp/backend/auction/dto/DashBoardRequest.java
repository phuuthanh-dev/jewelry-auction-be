package vn.webapp.backend.auction.dto;


public record DashBoardRequest (
        Integer yearGetRegisterAccount,
        Integer yearGetAuction,
        Integer yearGetRevenue,
        Integer yearGetAuctionFailedAndSuccess,
        Integer monthGetAuctionFailedAndSuccess,
        Integer yearGetJewelry,
        Integer monthGetJewelry,
        Integer yearGetUserJoinAuction
) {
}
