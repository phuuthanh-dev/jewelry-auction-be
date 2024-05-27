package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.repository.ImageRepository;
import vn.webapp.backend.auction.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/get-by-jewelry/{id}")
    public ResponseEntity<List<Image>> getByJewelry(@PathVariable Integer id)  {
        return ResponseEntity.ok(imageService.getImagesByJewelryId(id));
    }

    @GetMapping("/get-icon-jewelry/{id}")
    public ResponseEntity<Image> getIconByJewelry(@PathVariable Integer id)  {
        return ResponseEntity.ok(imageService.getImageByIconAndJewelryId(id));
    }
}
