package muck.core;

public class SignupResponse {
    private String username;
    private int resultCode;
    private String message;

    public SignupResponse() {

    }

    public SignupResponse(String username, int resultCode, String message) {
        this.username = username;
        this.resultCode = resultCode;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
