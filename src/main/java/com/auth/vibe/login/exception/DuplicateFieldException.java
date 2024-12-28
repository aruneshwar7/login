package com.auth.vibe.login.exception;

import java.util.List;

public class DuplicateFieldException extends RuntimeException {

    private final List<String> messages;

    public DuplicateFieldException(List<String> messages) {
        super(String.join(", ", messages)); // Concatenate messages for exception logging
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
