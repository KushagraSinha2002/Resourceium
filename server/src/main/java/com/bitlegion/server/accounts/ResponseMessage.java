package com.bitlegion.server.accounts;

import java.util.Optional;

public class ResponseMessage {
    private String message;
    private Optional<Integer> userId;
    private Optional<String> username;

    public ResponseMessage(String message, Optional<Integer> userId, Optional<String> username) {
        this.message = message;
        this.userId = userId;
        this.username = username;
    }

    public Optional<Integer> getUserId() {
        return this.userId;
    }

    public ResponseMessage setUserId(Optional<Integer> userId) {
        this.userId = userId;
        return this;
    }

    public Optional<String> getUsername() {
        return this.username;
    }

    public ResponseMessage setUsername(Optional<String> username) {
        this.username = username;
        return this;
    }

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
