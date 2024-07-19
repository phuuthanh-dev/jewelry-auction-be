package vn.webapp.backend.auction.dto;

public record JewelryCreateRequest(
        String username,
        String name,
        String token,
        String description,
        String category,
        Double buyNowPrice,
        String material,
        String brand,
        Double weight
) {
}
