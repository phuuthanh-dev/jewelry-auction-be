package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.service.AuctionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Auction>> getAll() {
        return ResponseEntity.ok(auctionService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Integer id) {
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Integer id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }
}
