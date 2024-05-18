package vn.webapp.backend.auction.service;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.dto.ActivateAccountRequest;
import vn.webapp.backend.auction.dto.AuthenticationRequest;
import vn.webapp.backend.auction.dto.AuthenticationResponse;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.exception.AccountDisabledException;
import vn.webapp.backend.auction.exception.AccountInactiveException;
import vn.webapp.backend.auction.exception.ExpiredTokenException;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.repository.UserRepository;
import vn.webapp.backend.auction.service.email.EmailService;


@Service

@RequiredArgsConstructor
public class AuthenticationService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request) throws MessagingException {
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
            var jwtToken = jwtService.generateToken(user);
            return new AuthenticationResponse(jwtToken);
        }
        return null;
    }

    public AuthenticationResponse activateAccount(ActivateAccountRequest request) throws MessagingException {
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
            return new AuthenticationResponse(request.token());
        }
    }
}
