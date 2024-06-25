package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.service.auction_registration.AuctionRegistrationService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-registration")
public class AuctionRegistrationController {

    private final AuctionRegistrationService auctionRegistrationService;

    // API to retrieve all registrations for a specific auction
    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<AuctionRegistration>> getRegistrationsForAuction(@PathVariable Integer auctionId) {
        List<AuctionRegistration> registrations = auctionRegistrationService.findByAuctionIdAndValid(auctionId);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<Page<AuctionRegistration>> getAuctionRegistrationsByUser(
            @RequestParam(defaultValue = "registrationDate") String sortBy,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int userId,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionRegistrationService.findByUserIdAndValid(userId, pageable));
    }
}
