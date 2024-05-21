package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.model.Role;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.service.RoleService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
