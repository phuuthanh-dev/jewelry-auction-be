package vn.webapp.backend.auction.service;

import vn.webapp.backend.auction.model.JewelryCategory;

import java.util.List;

public interface JewelryCategoryService {
    List<JewelryCategory> getAll();
    JewelryCategory getById(int id);
    JewelryCategory saveJewelryCategory(JewelryCategory jewelryCategory);
    void deleteJewelryCategory(Integer id);
}
