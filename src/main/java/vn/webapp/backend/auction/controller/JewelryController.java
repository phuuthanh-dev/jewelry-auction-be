package vn.webapp.backend.auction.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.JewelryCreateRequest;
import vn.webapp.backend.auction.dto.JewelryUpdateRequest;
import vn.webapp.backend.auction.dto.SendJewelryFromUserRequest;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.RequestApproval;
import vn.webapp.backend.auction.model.User;
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
            jewelries = jewelryService.getAllJewelries(pageable);
        }

        return ResponseEntity.ok(jewelries);
    }

    @GetMapping("/manager-list")
    public ResponseEntity<Page<Jewelry>> getAllJewelriesManager(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String jewelryName,
            @RequestParam(required = false) String category,
            @RequestParam JewelryState state,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        Page<Jewelry> jewelries;
        jewelries = jewelryService.getJewelriesManager(state,jewelryName, category, pageable);
        return ResponseEntity.ok(jewelries);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Jewelry>> getAll() {
        return ResponseEntity.ok(jewelryService.getAll());
    }

    @GetMapping("/get-by-category/{id}")
    public ResponseEntity<List<Jewelry>> getByCategory(@PathVariable  Integer id)  {
        return ResponseEntity.ok(jewelryService.getJewelriesByCategoryId(id));
    }

    @GetMapping("/get-by-name/{key}")
    public ResponseEntity<List<Jewelry>> getByCategory(@PathVariable  String key)  {
        return ResponseEntity.ok(jewelryService.getJewelriesByNameContain(key));
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
            @RequestParam(required = false) String jewelryName,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelryByStateAndIsHolding(state,isHolding,category,jewelryName,pageable));
    }

    @GetMapping("/jewelry-passed")
    public ResponseEntity<Page<Jewelry>> getRequestPassed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String jewelryName,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelryPassed(jewelryName, category, pageable));
    }

    @GetMapping("/return-violator")
    public ResponseEntity<Page<Jewelry>> getJewelryReturnedViolator(
            @RequestParam(required = false) String jewelryName,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelryReturnedViolator(category,jewelryName,pageable));
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


    @PutMapping("/set-state-holding/{id}")
    public ResponseEntity<Jewelry> setStateWithHolding(@PathVariable Integer id,  @RequestParam boolean isHolding, @RequestParam JewelryState state ) throws MessagingException {
        jewelryService.setStateWithHolding(id,isHolding,state);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Jewelry> updateById(@RequestBody JewelryUpdateRequest jewelry)   {
        return ResponseEntity.ok(jewelryService.updateJewelry(jewelry));
    }

    @PostMapping
    public ResponseEntity<Jewelry> createJewelry(@RequestBody JewelryCreateRequest jewelry) {
        return ResponseEntity.ok(jewelryService.createJewelry(jewelry));
    }

    @GetMapping("/user-jewelry/{userId}")
    public ResponseEntity<Page<Jewelry>> getJewelriesActiveByUserId(
            @PathVariable  Integer userId,
            @RequestParam(required = false) String jewelryName,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(jewelryService.getJewelriesActiveByUserId(userId, jewelryName,pageable));
    }

}
