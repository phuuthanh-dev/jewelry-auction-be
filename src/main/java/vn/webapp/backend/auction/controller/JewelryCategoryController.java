package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.JewelryCategory;
import vn.webapp.backend.auction.service.JewelryCategoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/jewelry-category")
public class JewelryCategoryController {

    private final JewelryCategoryService jewelryCategoryService;

    @GetMapping("/get-all")
    public ResponseEntity<List<JewelryCategory>> getAllJewelryCategories() {
        return ResponseEntity.ok(jewelryCategoryService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JewelryCategory> getJewelryCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(jewelryCategoryService.getById(id));
    }
}
