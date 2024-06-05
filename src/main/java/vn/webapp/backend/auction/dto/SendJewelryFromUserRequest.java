package vn.webapp.backend.auction.dto;

import java.util.List;

public record SendJewelryFromUserRequest(
        String name,
        String price,
        String description,
        String material,
        String weight,
        String brand,
        List<String> images


) {
//    id: 0,
//    name: '',
//    price: '',
//    description: '',
//    material: '',
//    brand: '',
//    weight: '',
//    images: '',
//    user: ''

}
