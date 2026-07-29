package com.really.good.sir.energy.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(final LocalDateTime timestamp, final int status, final String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}