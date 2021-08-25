package muck.server.Exceptions;

public class ModelNotFoundException extends Exception {
    public ModelNotFoundException() {
        super("The model you seek could not be found.");
    }
    public ModelNotFoundException(String message) {
        super(message);
    }

}
