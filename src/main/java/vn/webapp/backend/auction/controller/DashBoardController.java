package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.DashBoardRequest;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.service.dashboard.DashBoardService;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping
    public ResponseEntity<DashBoardResponse> getDashBoardTotal(@ModelAttribute DashBoardRequest dashBoardRequest) {
        return ResponseEntity.ok(dashBoardService.getInformation(dashBoardRequest));
    }
}
