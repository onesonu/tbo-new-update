package com.fastays.tbo_hotel_search.exception;

public class HotelSearchException extends RuntimeException {
    public HotelSearchException(String message) {
        super(message);
    }

    public HotelSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}

