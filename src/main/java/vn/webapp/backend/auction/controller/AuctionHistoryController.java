package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.BidRequest;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.AuctionHistory;
import vn.webapp.backend.auction.service.AuctionHistoryService;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-history")
public class AuctionHistoryController {

    private final AuctionHistoryService auctionHistoryService;

//    @GetMapping("/get-by-auction/{id}")
//    public ResponseEntity<List<AuctionHistory>> getAuctionHistoryByAuctionId(@PathVariable Integer id) {
//        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByAuctionId(id));
//    }

    @GetMapping("/get-by-auction")
    public ResponseEntity<Page<AuctionHistory>> getAuctionHistoryByAuctionId(
            @RequestParam(defaultValue = "time") String sortBy,
            @RequestParam(defaultValue = "0") Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByAuctionId(pageable, id));
    }

    @GetMapping("/get-by-username")
    public ResponseEntity<Page<AuctionHistory>> getAuctionHistoryByUsername(
            @RequestParam(defaultValue = "time") String sortBy,
            @RequestParam(defaultValue = "0") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByUsername(pageable, username));
    }

    @GetMapping("/get-by-date/{date}")
    public ResponseEntity<List<AuctionHistory>> getAuctionHistoryByDate(@PathVariable String date) {  //2024-12-10
        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByDate(date));
    }

    @PostMapping
    public ResponseEntity<?> saveBidByUserAndAuction(@RequestBody BidRequest request) {
        auctionHistoryService.saveBidByUserAndAuction(request);
        return ResponseEntity.ok().build();
    }
}
