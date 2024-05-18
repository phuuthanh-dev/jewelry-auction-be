package vn.webapp.backend.auction.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.dto.AuthenticationRequest;
import vn.webapp.backend.auction.dto.AuthenticationResponse;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request)  {
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Người dùng với username: " + request.username()
                                + " không tồn tại. Vui lòng đăng ký tài khoản mới."));
        if (user.getState() == AccountState.INACTIVE) {
//            emailService.sendActivationEmail(request.email(), user.getFullName(),
//                    jwtService.generateToken(user));
//            throw new AccountInactiveException("");
        } else if (user.getState() == AccountState.DISABLE) {
//            throw new AccountDisabledException("");
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
}
