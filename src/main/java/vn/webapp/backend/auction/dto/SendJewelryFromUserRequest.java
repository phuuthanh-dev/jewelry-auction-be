package vn.webapp.backend.auction.dto;

import vn.webapp.backend.auction.enums.JewelryState;

import java.util.List;

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
