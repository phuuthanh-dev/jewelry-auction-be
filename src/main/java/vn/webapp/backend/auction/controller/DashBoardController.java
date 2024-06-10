package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.service.DashBoardService;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping
    public ResponseEntity<DashBoardResponse> getDashBoardTotal() {
        return ResponseEntity.ok(dashBoardService.getInformation());
    }
}
