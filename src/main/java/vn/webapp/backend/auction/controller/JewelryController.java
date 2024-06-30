package vn.webapp.backend.auction.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.SendJewelryFromUserRequest;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.service.jewelry.JewelryService;

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
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String username) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);

        Page<Jewelry> jewelries;
        if (username != null) {
            jewelries = jewelryService.getJewelriesByUsername(username, pageable);
        } else {
            jewelries = jewelryService.getAllJeweries(pageable);
        }

        return ResponseEntity.ok(jewelries);
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
    public ResponseEntity<Page<Jewelry>> getJewelryInWaitlist(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelriesInWaitList(pageable));
    }

    @GetMapping("/is-holding")
    public ResponseEntity<Page<Jewelry>> getJewelryByHolding(
            @RequestParam JewelryState state,
            @RequestParam Boolean isHolding,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelryByStateAndIsHolding(state,isHolding,pageable));
    }

    @GetMapping("/in-handover-list")
    public ResponseEntity<Page<Jewelry>> getJewelryInHandOver(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelriesInHandOver(pageable));
    }

    @PostMapping("/jewelry-request")
    public ResponseEntity<Jewelry> requestJewelry(@RequestBody SendJewelryFromUserRequest request) {
        return ResponseEntity.ok(jewelryService.requestJewelry(request));
    }

    @GetMapping("/latest")
    public ResponseEntity<Jewelry> lastJewelry() {
        return ResponseEntity.ok(jewelryService.getLatestJewelry());
    }


    @PutMapping("/set-holding/{id}")
    public ResponseEntity<Jewelry> setHolding(@PathVariable Integer id,  @RequestParam boolean state) throws MessagingException {
        jewelryService.setHolding(id,state);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-jewelry/{userId}")
    public ResponseEntity<Page<Jewelry>> getJewelriesActiveByUserId(
            @PathVariable  Integer userId,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelriesActiveByUserId(userId,pageable));
    }

}
