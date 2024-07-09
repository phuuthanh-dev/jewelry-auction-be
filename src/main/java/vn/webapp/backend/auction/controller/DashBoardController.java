package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.service.dashboard.DashBoardService;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping
    public ResponseEntity<DashBoardResponse> getDashBoardTotal(
            @RequestParam("yearGetRegisterAccount") Integer yearGetRegisterAccount,
            @RequestParam("yearGetAuction") Integer yearGetAuction,
            @RequestParam("yearGetRevenue") Integer yearGetRevenue,
            @RequestParam("yearGetAuctionFailedAndSuccess") Integer yearGetAuctionFailedAndSuccess,
            @RequestParam("monthGetAuctionFailedAndSuccess") Integer monthGetAuctionFailedAndSuccess,
            @RequestParam("yearGetUserJoinAuction") Integer yearGetUserJoinAuction
    ) {
        return ResponseEntity.ok(dashBoardService.getInformation(yearGetRegisterAccount, yearGetAuction, yearGetRevenue,
                yearGetAuctionFailedAndSuccess, monthGetAuctionFailedAndSuccess,
                yearGetUserJoinAuction));
    }
}
