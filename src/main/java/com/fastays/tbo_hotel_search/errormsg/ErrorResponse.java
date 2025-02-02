package com.fastays.tbo_hotel_search.errormsg;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String errorCode;      // A unique error code
    private String message;        // The error message
    private LocalDateTime timestamp;  // Timestamp when the error occurred
    private String details;        // Optional field for more detailed error information
    private int status;            // HTTP status code (optional)
    private String path;           // The request URL path (optional)

    public ErrorResponse(String errorCode, String message, LocalDateTime timestamp, int status, String path) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
    }
}
