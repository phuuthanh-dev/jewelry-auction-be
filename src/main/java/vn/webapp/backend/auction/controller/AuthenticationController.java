package vn.webapp.backend.auction.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.*;
import vn.webapp.backend.auction.dto.RegisterAccountRequest;
import vn.webapp.backend.auction.service.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest) throws MessagingException {
        return ResponseEntity.ok(authenticationService.authenticate(request, httpServletRequest));
    }

    @PostMapping("/activation")
    public ResponseEntity<AuthenticationResponse> activateAccount(
            @RequestBody ActivateAccountRequest request) throws MessagingException {
        authenticationService.activateAccount(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterAccountRequest request, HttpServletRequest httpServletRequest) throws MessagingException {
        return ResponseEntity.ok(authenticationService.register(request, httpServletRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/rotate-refresh-token")
    public void rotateRefreshToken(HttpServletRequest request,
                                   HttpServletResponse response
    ) throws IOException {
        authenticationService.rotateRefreshToken(request, response);
    }
}
