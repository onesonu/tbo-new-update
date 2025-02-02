package com.fastays.tbo_hotel_search.exceptionHandler;

import com.fastays.tbo_hotel_search.errorMsg.ErrorResponse;
import com.fastays.tbo_hotel_search.exception.HotelSearchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle HotelSearchException
    @ExceptionHandler(HotelSearchException.class)
    public ResponseEntity<ErrorResponse> handleHotelSearchException(HotelSearchException ex) {
        String requestUri = getRequestUri();

        ErrorResponse errorResponse = new ErrorResponse(
                "HOTEL_SEARCH_ERROR",        // Custom error code
                ex.getMessage(),             // Error message
                LocalDateTime.now(),         // Current timestamp
                HttpStatus.BAD_REQUEST.value(), // HTTP status code
                requestUri                   // Path of the request that caused the error
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        String requestUri = getRequestUri();

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",     // Custom error code
                "An unexpected error occurred: " + ex.getMessage(), // Error message
                LocalDateTime.now(),         // Current timestamp
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // HTTP status code
                requestUri                   // Path of the request that caused the error
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to extract the request URI
    private String getRequestUri() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getRequestURI();  // Should work with Jakarta HttpServletRequest
        }
        return "Unknown";  // If request is not available, return a default value
    }

}



