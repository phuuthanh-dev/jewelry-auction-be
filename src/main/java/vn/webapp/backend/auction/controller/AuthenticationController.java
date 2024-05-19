package vn.webapp.backend.auction.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.ActivateAccountRequest;
import vn.webapp.backend.auction.dto.AuthenticationRequest;
import vn.webapp.backend.auction.dto.AuthenticationResponse;
import vn.webapp.backend.auction.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request) throws MessagingException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/activation")
    public ResponseEntity<AuthenticationResponse> activateAccount (
            @RequestBody ActivateAccountRequest request) throws MessagingException {
        authenticationService.activateAccount(request);
        return ResponseEntity.ok(authenticationService.activateAccount(request));
    }
}
