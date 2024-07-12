package vn.webapp.backend.auction.service.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.dto.*;
import vn.webapp.backend.auction.dto.RegisterAccountRequest;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.enums.TokenType;
import vn.webapp.backend.auction.exception.*;
import vn.webapp.backend.auction.model.Bank;
import vn.webapp.backend.auction.model.Token;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.BankRepository;
import vn.webapp.backend.auction.repository.TokenRepository;
import vn.webapp.backend.auction.repository.UserRepository;
import vn.webapp.backend.auction.service.jwt.JwtService;
import vn.webapp.backend.auction.service.user.UserService;
import vn.webapp.backend.auction.service.email.EmailService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BankRepository bankRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private static final String USER_AGENT_HEADER = "User-Agent";

    @Override
    public AuthenticationResponse authenticateGeneral(AuthenticationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws MessagingException {
        var user = userService.findUserByUsernameOrEmail(request.username());
        try {
            checkUserStateForGeneral(user, request.username());

            authenticateUser(request.username(), request.password());
            return generateAuthenticationResponse(user, httpServletRequest, httpServletResponse);
        } catch (AccountDisabledException e) {
            return AuthenticationResponse.builder()
                    .banReason(user.getBanReason())
                    .build();
        }
    }

    @Override
    public AuthenticationResponse authenticateAdminManager(AuthenticationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        var user = userService.findUserByUsernameOrEmail(request.username());
        checkUserStateForAdminManager(user, request.username());

        authenticateUser(request.username(), request.password());
        checkUserRole(user, request.username());

        return generateAuthenticationResponse(user, httpServletRequest, httpServletResponse);
    }

    private void checkUserStateForGeneral(User user, String username) throws MessagingException {
        if (user.getState() == AccountState.INACTIVE) {
            emailService.sendActivationEmail(user.getEmail(), user.getFullName(),
                    jwtService.generateToken(user));
            throw new AccountInactiveException("Tài khoản chưa kích hoạt, vui lòng kiểm tra email để kích hoạt tài khoản.");
        } else if (user.getState() == AccountState.DISABLE) {
            throw new AccountDisabledException("Tài khoản với username: " + username + " đã bị vô hiệu hóa.");
        }
    }

    private void checkUserRole(User user, String username) {
        Role userRole = user.getRole();
        boolean isAuthorized = userRole == Role.MANAGER || userRole == Role.ADMIN;
        if (!isAuthorized) {
            throw new UnauthorizedException("Người dùng với: " + username + " không có quyền truy cập.");
        }
    }

    private void checkUserStateForAdminManager(User user, String username) {
        if (user.getState() == AccountState.DISABLE) {
            throw new AccountDisabledException("Tài khoản với username: " + username + " đã bị vô hiệu hóa.");
        }
    }

    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }

    private AuthenticationResponse generateAuthenticationResponse(User user, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String ipAddress = httpServletRequest.getRemoteAddr();
        String deviceInfo = httpServletRequest.getHeader(USER_AGENT_HEADER);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        ResponseCookie refreshTokenCookie = jwtService.generateRefreshTokenCookie(refreshToken);
        httpServletResponse.addHeader("Set-Cookie", refreshTokenCookie.toString());

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken, refreshToken, ipAddress, deviceInfo);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken, String refreshToken, String ipAddress, String deviceInfo) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .createdTime(LocalDateTime.now())
                .ipAddress(ipAddress)
                .deviceInfo(deviceInfo)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void activateAccount(ActivateAccountRequest request) throws MessagingException {
        var username = jwtService.extractUsername(request.token());
        var user = userRepository.findByUsername(username)
                .orElseThrow();
        if (jwtService.isTokenExpired(request.token())) {
            var jwtToken = jwtService.generateToken(user);
            emailService.sendActivationEmail(username, user.getFullName(), jwtToken);
            throw new ExpiredTokenException(
                    "Mã kích hoạt đã hết hạn. Vui lòng kiểm tra email để nhận hướng dẫn kích hoạt mới.");
        } else {
            user.setState(AccountState.ACTIVE);
            userRepository.save(user);
        }
    }

    @Override
    public AuthenticationResponse register(RegisterAccountRequest request, HttpServletRequest httpServletRequest) throws MessagingException {
        userRepository.findByUsername(request.username())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Người dùng với username: " + request.username() + " đã tồn tại.");
                });
        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Người dùng với email: " + request.email() + " đã tồn tại.");
                });
        Bank bank = bankRepository.findById(request.bankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank with ID: " + request.bankId() + " not found."));

        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .address(request.address())
                .district(request.district())
                .ward(request.ward())
                .city(request.city())
                .phone(request.phone())
                .avatar("https://www.iconpacks.net/icons/2/free-user-icon-3296-thumb.png")
                .yob(request.yob())
                .role(request.role())
                .cccd(request.cccd())
                .cccdFirst(request.cccdFirst())
                .cccdLast(request.cccdLast())
                .cccdFrom(request.cccdFrom())
                .registerDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))))
                .state(AccountState.INACTIVE)
                .bankAccountName(request.bankAccountName())
                .bankAccountNumber(request.bankAccountNumber())
                .bank(bank)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        String ipAddress = httpServletRequest.getRemoteAddr();
        String deviceInfo = httpServletRequest.getHeader(USER_AGENT_HEADER);

        saveUserToken(savedUser, jwtToken, refreshToken, ipAddress, deviceInfo);
        emailService.sendActivationEmail(request.email(), user.getFullName(), jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
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

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String username;

        final String oldRefreshToken = jwtService.getTokenFromCookie(request, "refresh_token");
        if (oldRefreshToken != null) {
            username = jwtService.extractUsername(oldRefreshToken);
            if (username != null) {
                var user = userRepository.findByUsername(username)
                        .orElseThrow();
                if (jwtService.isTokenValid(oldRefreshToken, user)) {
                    var tokenOptional = tokenRepository.findByRefreshToken(oldRefreshToken);

                    if (tokenOptional.isPresent() && !tokenOptional.get().expired && !tokenOptional.get().revoked) {
                        var accessToken = jwtService.generateToken(user);
                        var newRefreshToken = jwtService.generateRefreshToken(user);
                        revokeAllUserTokens(user);

                        String ipAddress = request.getRemoteAddr();
                        String deviceInfo = request.getHeader(USER_AGENT_HEADER);

                        saveUserToken(user, accessToken, newRefreshToken, ipAddress, deviceInfo);

                        ResponseCookie refreshTokenCookie = jwtService.generateRefreshTokenCookie(newRefreshToken);
                        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

                        var authResponse = AuthenticationResponse.builder()
                                .accessToken(accessToken)
                                .build();
                        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired or revoked");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token");
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token not found");
        }
    }

    @Override
    @Transactional
    public AuthenticationResponse changePassword(ChangePasswordRequest request) {
        var user = userRepository.findByUsername(jwtService.extractUsername(request.token()))
                .orElseThrow();
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new OldPasswordMismatchException("Mật khẩu cũ không đúng.");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) throws MessagingException {
        var existingUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(
                        "Người dùng với email '" + request.email() + "' không tồn tại."));
        if (existingUser.getRole() != Role.MEMBER) {
            throw new UnauthorizedException(
                    "Bạn không có quyền truy cập. Vui lòng liên hệ quản trị viên để được hỗ trợ.");
        }
        emailService.sendResetPasswordEmail(request.email(), existingUser.getFullName(),
                jwtService.generateToken(existingUser));
    }

    @Override
    public AuthenticationResponse resetPassword(ResetPasswordRequest request) {
        var username = jwtService.extractUsername(request.token());
        var user = userRepository.findByUsername(username)
                .orElseThrow();
        if (user.getRole() == Role.MEMBER) {
            user.setPassword(passwordEncoder.encode(request.password()));
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } else {
            throw new UnauthorizedException("Không có quyền truy cập.");
        }
    }
}
