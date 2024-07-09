package vn.webapp.backend.auction.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.AuctionRegistrationDTO;
import vn.webapp.backend.auction.dto.AuctionRequest;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.service.auction.AuctionService;
import vn.webapp.backend.auction.service.email.EmailService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction")
public class AuctionController {
    private final AuctionService auctionService;
    private final EmailService emailService;

    @GetMapping("/sorted-and-paged")
    public ResponseEntity<Page<Auction>> getAllAuctionsSortedAndPaged(
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(required = false) String auctionName,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        AuctionState auctionState = resolveAuctionState(state);
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionService.getAllAuctions(auctionState, pageable, auctionName, categoryId));
    }

    @GetMapping("/get-by-day/{startDate}/{endDate}")
    public ResponseEntity<List<Auction>> getAllAuctionsSortedAndPaged(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        return ResponseEntity.ok(auctionService.findAuctionSortByBetweenStartdayAndEndday(startDate, endDate));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Auction>> getAll() {
        return ResponseEntity.ok(auctionService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Integer id) {
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    @GetMapping("/get-top-3-price")
    public ResponseEntity<List<Auction>> getTop3Auction(@RequestParam List<AuctionState> state) {
        List<Auction> top3Auctions = auctionService.findTop3AuctionsByPriceAndState(state);
        return ResponseEntity.ok(top3Auctions);
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

    @GetMapping("/get-by-state")
    public List<Auction> getAuctionsByState(@RequestParam AuctionState state) {
        return auctionService.getAuctionByState(state);
    }

    @GetMapping("/get-by-states")
    public ResponseEntity<Page<Auction>> getAllAuctionsSortedAndPaged(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "") List<AuctionState> states,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionService.getAuctionsByStates(states, pageable));
    }


    @GetMapping("/get-current-by-jewelry/{id}")
    public ResponseEntity<Auction> getCurrentAuctionByJewelryId(@PathVariable Integer id) {
        return ResponseEntity.ok(auctionService.getCurrentAuctionByJewelryId(id));
    }

    @GetMapping("/get-by-staff/{id}")
    public ResponseEntity<Page<Auction>> getAuctionByStaffId(
            @PathVariable Integer id,
            @RequestParam(required = false) String auctionName,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionService.getByStaffID(id, auctionName, pageable));
    }

    @PostMapping("/create-new")
    public ResponseEntity<Auction> createNewAuction(@RequestBody AuctionRequest request) {
        return ResponseEntity.ok(auctionService.createNewAuction(request));
    }

    private AuctionState resolveAuctionState(String state) {
        if ("ALL".equalsIgnoreCase(state)) {
            return null;
        } else {
            return AuctionState.valueOf(state);
        }
    }

    @GetMapping("/get-auction-registration")
    public ResponseEntity<Page<AuctionRegistrationDTO>> getAuctionRegistrations(
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(required = false) String auctionName,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        AuctionState auctionState = resolveAuctionState(state);

        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionService.getAuctionRegistrations(auctionState, auctionName, pageable));
    }

    @GetMapping("/delete-result/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Integer id) throws MessagingException {
        auctionService.deleteAuctionResult(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-failed-auctions")
    public ResponseEntity<Page<Auction>> getAllFailedAuctions(
            @RequestParam(defaultValue = "endDate") String sortBy,
            @RequestParam(required = false) String auctionName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(auctionService.getAllFailedAuctions(pageable, auctionName));
    }
}
