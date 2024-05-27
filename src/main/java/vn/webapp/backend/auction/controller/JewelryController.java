package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/sorted-and-paged")
    public ResponseEntity<Page<Jewelry>> getAllJewelriesSortedAndPaged(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getAllJeweries(pageable));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Jewelry>> getAll() {
        return ResponseEntity.ok(jewelryService.getAll());
    }

    @GetMapping("/get-by-category/{id}")
    public ResponseEntity<List<Jewelry>> getByCategory(@PathVariable  Integer id)  {
        return ResponseEntity.ok(jewelryService.getJeweriesByCategoryId(id));
    }

    @GetMapping("/get-by-name/{key}")
    public ResponseEntity<List<Jewelry>> getByCategory(@PathVariable  String key)  {
        return ResponseEntity.ok(jewelryService.getJeweriesByNameContain(key));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Jewelry> getJewelryById(@PathVariable Integer id) {
        return ResponseEntity.ok(jewelryService.getJewelryById(id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Jewelry>> getJewelryByUsername(@PathVariable String username) {
        return ResponseEntity.ok(jewelryService.getJewelryByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJewelry(@PathVariable Integer id) {
        jewelryService.deleteJewelry(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/in-wait-list")
    public ResponseEntity<List<Jewelry>> getJewelryInWaitlist() {
        return ResponseEntity.ok(jewelryService.getJewelriesInWaitList());
    }

}
