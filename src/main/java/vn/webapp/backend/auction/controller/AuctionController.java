package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.service.AuctionService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/sorted-and-paged")
    public ResponseEntity<Page<Auction>> getAllAuctionsSortedAndPaged(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionService.getAllAuctions(pageable));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Auction>> getAll() {
        return ResponseEntity.ok(auctionService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Integer id) {
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    @GetMapping("/get-by-day/{start}/{end}")
    public ResponseEntity<List<Auction>> getAuctionByDay(@PathVariable Timestamp start, @PathVariable Timestamp end ) {
        return ResponseEntity.ok(auctionService.findAuctionSortByBetweenStartdayAndEndday(start,end));
    }

    @GetMapping("/get-by-name/{key}")
    public ResponseEntity<List<Auction>> getAuctionByName(@PathVariable String key) {
        return ResponseEntity.ok(auctionService.findAuctionByName(key));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Integer id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/set-state/{id}")
    public ResponseEntity<Auction> setState(@PathVariable Integer id, @RequestParam String state) {
        auctionService.setAuctionState(id, state);
        return ResponseEntity.ok().build();
    }
}
