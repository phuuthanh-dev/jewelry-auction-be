package vn.webapp.backend.auction.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.*;
import vn.webapp.backend.auction.dto.RegisterAccountRequest;
import vn.webapp.backend.auction.service.AuthenticationServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateGeneral(
            @RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws MessagingException {

        AuthenticationResponse authenticationResponse = authenticationService.authenticateGeneral(request, httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @PostMapping("/authenticate-admin-manager")
    public ResponseEntity<AuthenticationResponse> authenticateAdminManager(
            @RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws MessagingException {

        AuthenticationResponse authenticationResponse = authenticationService.authenticateAdminManager(request, httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @PostMapping("/change-password")
    public ResponseEntity<AuthenticationResponse> changePassword(
            @RequestBody ChangePasswordRequest request) {
        ResponseEntity.ok(authenticationService.changePassword(request));
        return ResponseEntity.ok().build();
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

    @PostMapping("/forgot-password")
    public ResponseEntity<AuthenticationResponse> forgotPassword(
            @RequestBody ForgotPasswordRequest request) throws MessagingException {
        authenticationService.forgotPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthenticationResponse> resetPassword(
            @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authenticationService.resetPassword(request));
    }


}
