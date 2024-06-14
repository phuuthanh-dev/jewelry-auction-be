package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.BidRequest;
import vn.webapp.backend.auction.enums.AuctionHistoryState;
import vn.webapp.backend.auction.model.AuctionHistory;
import vn.webapp.backend.auction.service.AuctionHistoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-history")
public class AuctionHistoryController {

    private final AuctionHistoryService auctionHistoryService;

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

    @GetMapping("/get-by-auction-and-user")
    public ResponseEntity<Page<AuctionHistory>> getAuctionHistoryByAuctionIdAndUserId(
            @RequestParam(defaultValue = "time") String sortBy,
            @RequestParam(defaultValue = "0") Integer auctionId,
            @RequestParam(defaultValue = "0") Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ACTIVE") AuctionHistoryState state,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByAuctionIdAndUserId(auctionId, userId, state, pageable));
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


    @GetMapping("/get-when-auction-finished/{id}")
    public ResponseEntity<List<AuctionHistory>> getAuctionHistoryByAuctionIdWhenFinished(@PathVariable Integer id) {  //2024-12-10
        return ResponseEntity.ok(auctionHistoryService.getAuctionHistoryByAuctionIdWhenFinished(id));
    }

    @PostMapping
    public ResponseEntity<Void> saveBidByUserAndAuction(@RequestBody BidRequest request) {
        auctionHistoryService.saveBidByUserAndAuction(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bids/{userId}/{auctionId}")
    public ResponseEntity<Void> deleteBidByUserAndAuction(@PathVariable Integer userId, @PathVariable Integer auctionId) {
        auctionHistoryService.deleteBidByUserAndAuction(userId, auctionId);
        return ResponseEntity.ok().build();
    }
}
