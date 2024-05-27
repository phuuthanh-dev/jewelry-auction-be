package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.service.TransactionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<List<Transaction>> getTransactionByUsername(@PathVariable String username) {
        return ResponseEntity.ok(transactionService.getTransactionsByUsername(username));
    }

    @PutMapping("/set-state/{id}")
    public ResponseEntity<Transaction> setState(@PathVariable Integer id, @RequestParam String state) {
        transactionService.setTransactionState(id, state);
        return ResponseEntity.ok().build();
    }
}
