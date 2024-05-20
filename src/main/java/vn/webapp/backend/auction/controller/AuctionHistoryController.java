package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.AuctionHistory;
import vn.webapp.backend.auction.service.AuctionHistoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/aution-history")
public class AuctionHistoryController {

    private final AuctionHistoryService auctionHistoryService;

    @GetMapping("/get-by-auction/{id}")
    public ResponseEntity<List<AuctionHistory>> getAuctionHistoryByAuctionId(@PathVariable Integer id) {
        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByAuctionId(id));
    }

    @GetMapping("/get-by-username/{username}")
    public ResponseEntity<List<AuctionHistory>> getAuctionHistoryByUsername(@PathVariable String username) {
        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByUsername(username));
    }
}
