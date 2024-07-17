package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.enums.JewelryMaterial;

public record SendJewelryFromUserRequest(
        Integer userId,
        Double buyNowPrice,
        String description,
        JewelryMaterial material,
        Double weight,
        String brand,
        String category,
        String name

) {

}
