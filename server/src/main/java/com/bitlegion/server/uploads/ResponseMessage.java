package com.bitlegion.server.uploads;

public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
