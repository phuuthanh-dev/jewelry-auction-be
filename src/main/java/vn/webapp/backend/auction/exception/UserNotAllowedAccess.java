package vn.webapp.backend.auction.exception;

public class UserNotAllowedAccess extends RuntimeException {
    public UserNotAllowedAccess(String message) {
        super(message);
    }
}
