package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.webapp.backend.auction.model.Bank;
import vn.webapp.backend.auction.service.bank.BankService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BankController {
    private final BankService bankService;

    @GetMapping
    public ResponseEntity<List<Bank>> getAll() {
        return ResponseEntity.ok(bankService.getAll());
    }
}
