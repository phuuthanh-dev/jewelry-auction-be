package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.ManagerRequestApproval;
import vn.webapp.backend.auction.dto.StaffRequestApproval;
import vn.webapp.backend.auction.dto.UserRequestApproval;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.RequestApproval;
import vn.webapp.backend.auction.service.RequestApprovalService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/request-approval")
public class RequestApprovalController {
    private final RequestApprovalService requestApprovalService;

    @GetMapping("/id/{id}")
    public ResponseEntity<RequestApproval> getRequestById(@PathVariable Integer id) {
        return ResponseEntity.ok(requestApprovalService.getRequestById(id));
    }

    @PutMapping("/set-state/{id}")
    public ResponseEntity<RequestApproval> setState(@PathVariable Integer id, @RequestParam String state) {
        requestApprovalService.setRequestState(id, state);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<RequestApproval> confirmRequest(@PathVariable Integer id, @RequestParam Integer responderId) {
        requestApprovalService.confirmRequest(id, responderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sender/{role}")
    public ResponseEntity<Page<RequestApproval>> getRequestByRoleOfSender(
            @PathVariable Role role,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(requestApprovalService.getRequestBySenderRole(role,pageable));
    }

    @PostMapping("/send-from-user")
    public ResponseEntity<RequestApproval> newRequestJewelryFromUser(@RequestBody UserRequestApproval request) {
        return ResponseEntity.ok(requestApprovalService.requestFromUser(request));
    }


    @PostMapping("/send-from-staff")
    public ResponseEntity<RequestApproval> newRequestJewelryFromStaff(@RequestBody StaffRequestApproval request) {
        return ResponseEntity.ok(requestApprovalService.requestFromStaff(request));
    }

    @PostMapping("/send-from-manager")
    public ResponseEntity<RequestApproval> newRequestJewelryFromManager(@RequestBody ManagerRequestApproval request) {
        return ResponseEntity.ok(requestApprovalService.requestFromManager(request));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<RequestApproval>> getRequestApprovalByUserId(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(requestApprovalService.getRequestApprovalByUserId(id,pageable));
    }
}
