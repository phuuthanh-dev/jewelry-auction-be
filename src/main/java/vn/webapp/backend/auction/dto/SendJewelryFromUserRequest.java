package vn.webapp.backend.auction.dto;

public record SendJewelryFromUserRequest(
        Integer userId,
        Double buyNowPrice,
        String description,
        String material,
        Double weight,
        String brand,
        String category,
        String name

) {

}
