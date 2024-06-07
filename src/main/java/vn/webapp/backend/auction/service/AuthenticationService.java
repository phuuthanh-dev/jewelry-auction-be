package vn.webapp.backend.auction.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.webapp.backend.auction.dto.*;
import vn.webapp.backend.auction.model.User;

import java.io.IOException;

public interface AuthenticationService {
    public AuthenticationResponse authenticate
            (AuthenticationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws MessagingException;


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
