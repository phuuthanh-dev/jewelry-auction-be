package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.RegisterAccountRequest;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.model.Role;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/member")
    public ResponseEntity<Page<User>> getMember(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) AccountState state,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(userService.getMemberByFullNameContainingAndState(fullName, state, pageable));
    }

    @GetMapping("/staff")
    public ResponseEntity<Page<User>> getStaff(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) AccountState state,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = (sortOrder.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortBy);
        return ResponseEntity.ok(userService.getStaffByFullNameContainingAndRoleAndState(fullName, roleId, state, pageable));
    }

    @PutMapping("/set-state/{id}")
    public ResponseEntity<User> setState(@PathVariable Integer id, @RequestParam String state) {
        userService.setAccountState(id, state);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/staff/register")
    public ResponseEntity<User> addStaff(
            @RequestBody RegisterAccountRequest user) {
        return ResponseEntity.ok(userService.registerStaff(user));
    }
}
