package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.enums.AuctionRegistrationState;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.service.AuctionRegistrationService;
import vn.webapp.backend.auction.service.UserService;

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
}
