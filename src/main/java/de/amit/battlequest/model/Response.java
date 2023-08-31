package de.amit.battlequest.model;

public class Response {
    private boolean isSuccess;
    private String message;

    public Response(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }
    public boolean isSuccessful() {
        return isSuccess;
    }
    public void setIsSuccess(boolean success) {
        this.isSuccess = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
