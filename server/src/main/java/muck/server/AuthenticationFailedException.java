package muck.server;

/**
 * Exception to indicate the supplied credentials are invalid.
 */
public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
