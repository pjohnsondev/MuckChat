package muck.core;

/**
 * Class that represents the result of a login
 */
public class LoginResponse {
    private String username;
    private int resultCode;
    private String message;

    public LoginResponse() {

    }

    public LoginResponse(String username, int resultCode, String message) {
        this.username = username;
        this.resultCode = resultCode;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
