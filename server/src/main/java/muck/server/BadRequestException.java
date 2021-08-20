package muck.server;

/**
 * Exception to indicate the supplied data is invalid or malformed
 */
public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
