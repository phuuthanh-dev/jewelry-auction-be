package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.enums.JewelryMaterial;

import java.sql.Timestamp;

public record JewelryUpdateRequest(
        Integer id,
        String name,
        String description,
        String category,
        Double buyNowPrice,
        JewelryMaterial material,
        String brand,
        Double weight,
        Timestamp createDate,
        String image,
        String state
) {
}
