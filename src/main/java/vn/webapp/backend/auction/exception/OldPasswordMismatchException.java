package vn.webapp.backend.auction.exception;

public class OldPasswordMismatchException extends RuntimeException {
    public OldPasswordMismatchException(String message) {
        super(message);
    }
}
