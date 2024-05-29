package vn.webapp.backend.auction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.dto.*;
import vn.webapp.backend.auction.dto.RegisterAccountRequest;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.TokenType;
import vn.webapp.backend.auction.exception.*;
import vn.webapp.backend.auction.model.Bank;
import vn.webapp.backend.auction.model.Token;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.BankRepository;
import vn.webapp.backend.auction.repository.TokenRepository;
import vn.webapp.backend.auction.repository.UserRepository;
import vn.webapp.backend.auction.service.email.EmailService;

import java.io.IOException;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BankRepository bankRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletRequest httpServletRequest) throws MessagingException {
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Người dùng với username: " + request.username()
                                + " không tồn tại. Vui lòng đăng ký tài khoản mới."));
        if (user.getState() == AccountState.INACTIVE) {
            emailService.sendActivationEmail(user.getEmail(), user.getFullName(),
                    jwtService.generateToken(user));
            throw new AccountInactiveException("Tài khoản chưa kích hoạt, vui lòng kiểm tra email để kích hoạt tài khoản.");
        } else if (user.getState() == AccountState.DISABLE) {
            throw new AccountDisabledException("Tài khoản với username: " + request.username() + " đã bị vô hiệu hóa.");
        } else if (user.getState() == AccountState.ACTIVE) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()));

            String ipAddress = httpServletRequest.getRemoteAddr();
            String deviceInfo = httpServletRequest.getHeader("User-Agent");

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken, ipAddress, deviceInfo);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        return null;
    }

    private void saveUserToken(User user, String jwtToken, String ipAddress, String deviceInfo) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .createdTime(LocalDateTime.now())
                .ipAddress(ipAddress)
                .deviceInfo(deviceInfo)
                .build();
        tokenRepository.save(token);
    }

    public void activateAccount(ActivateAccountRequest request) throws MessagingException {
        var username = jwtService.extractUsername(request.token());
        var user = userRepository.findByUsername(username)
                .orElseThrow();
        if (jwtService.isTokenExpired(request.token())) {
            emailService.sendActivationEmail(username, user.getFullName(), jwtService.generateToken(user));
            throw new ExpiredTokenException(
                    "Email kích hoạt đã hết hạn. Vui lòng kiểm tra email để nhận hướng dẫn kích hoạt mới.");
        } else {
            user.setState(AccountState.ACTIVE);
            userRepository.save(user);
        }
    }

    public AuthenticationResponse register(RegisterAccountRequest request, HttpServletRequest httpServletRequest) throws MessagingException {
        userRepository.findByUsername(request.username())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Người dùng với username: " + request.username() + " đã tồn tại.");
                });
        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Người dùng với email: " + request.email() + " đã tồn tại.");
                });
        Bank bank = bankRepository.findById(request.bankId()).get();
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .address(request.address())
                .province(request.province())
                .city(request.city())
                .phone(request.phone())
                .yob(request.yob())
                .role(request.role())
                .CCCD(request.CCCD())
                .state(AccountState.INACTIVE)
                .bankAccountName(request.bankAccountName())
                .bankAccountNumber(request.bankAccountNumber())
                .bank(bank)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        String ipAddress = httpServletRequest.getRemoteAddr();
        String deviceInfo = httpServletRequest.getHeader("User-Agent");

        saveUserToken(savedUser, jwtToken,ipAddress, deviceInfo);
        emailService.sendActivationEmail(request.email(), user.getFullName(), jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                revokeAllUserTokens(user);

                var accessToken = jwtService.generateToken(user);


                String ipAddress = request.getRemoteAddr();
                String deviceInfo = request.getHeader("User-Agent");

                saveUserToken(user, accessToken, ipAddress, deviceInfo);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void rotateRefreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                refreshToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);


                String ipAddress = request.getRemoteAddr();
                String deviceInfo = request.getHeader("User-Agent");

                saveUserToken(user, accessToken, ipAddress, deviceInfo);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
