package vn.webapp.backend.auction.service.authenticate;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.webapp.backend.auction.dto.*;

import java.io.IOException;

public interface AuthenticationService {
    public AuthenticationResponse authenticateGeneral
            (AuthenticationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws MessagingException;
    AuthenticationResponse authenticateAdminManager
            (AuthenticationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    public void activateAccount(ActivateAccountRequest request) throws MessagingException;

    public AuthenticationResponse register(RegisterAccountRequest request, HttpServletRequest httpServletRequest) throws MessagingException;

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

    public AuthenticationResponse changePassword(ChangePasswordRequest request);

    public void forgotPassword(ForgotPasswordRequest request) throws MessagingException;

    public AuthenticationResponse resetPassword(ResetPasswordRequest request);

}