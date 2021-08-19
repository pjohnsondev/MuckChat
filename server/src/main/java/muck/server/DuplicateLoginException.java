package muck.server;

/**
 * Each user can only log in once. If a user attempts to log in a second time, this exception will be thrown.
 */
public class DuplicateLoginException extends Exception {

    public DuplicateLoginException(String username) {
        super(String.format("Attempted to login with ID %s, but this ID is already logged in.", username));
    }
}
