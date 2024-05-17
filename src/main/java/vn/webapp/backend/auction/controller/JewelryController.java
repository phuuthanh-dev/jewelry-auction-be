package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.service.JewelryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/jewelry")
public class JewelryController {

    private final JewelryService jewelryService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Jewelry>> getAll() {
        return ResponseEntity.ok(jewelryService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Jewelry> getJewelryById(@PathVariable Integer id) {
        return ResponseEntity.ok(jewelryService.getJewelryById(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Jewelry>> getJewelryByUsername(@PathVariable String username) {
        return ResponseEntity.ok(jewelryService.getJewelryByUsername(username));
    }
}
