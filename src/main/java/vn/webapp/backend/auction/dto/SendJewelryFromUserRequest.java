package vn.webapp.backend.auction.dto;

public record SendJewelryFromUserRequest(
        Integer userId,
        Double price,
        String description,
        String material,
        Double weight,
        String brand,
        String category,
        String name

) {

}
