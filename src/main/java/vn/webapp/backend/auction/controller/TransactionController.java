package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.UserTransactionResponse;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.service.TransactionService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/get-by-user-name/{username}")
    public ResponseEntity<UserTransactionResponse> getTransactionByUsername(@PathVariable String username) {
        return ResponseEntity.ok(transactionService.getTransactionsDashboardByUsername(username));
    }

    @PutMapping("/set-state/{id}")
    public ResponseEntity<Transaction> setState(@PathVariable Integer id, @RequestParam String state) {
        transactionService.setTransactionState(id, state);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-username")
    public ResponseEntity<Page<Transaction>> getAuctionHistoryByUsername(
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "0") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(transactionService.getTransactionsByUsername(username, pageable));
    }

    @GetMapping("/get-by-type-state")
    public ResponseEntity<Page<Transaction>> getTransactionByTypeAndState(
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "PAYMENT_TO_WINNER") TransactionType type,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "SUCCEED") TransactionState state,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(transactionService.getTransactionByTypeAndState(type, state, pageable));
    }

    @PostMapping("/create-transaction-for-winner/{auctionId}")
    public ResponseEntity<User> createTransactionForWinner(@PathVariable Integer auctionId) {
        return ResponseEntity.ok(transactionService.createTransactionForWinner(auctionId));
    }
}
