package com.parkking.parkingservice.exception;

public class OtpMisMatchException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public OtpMisMatchException(String message) {
        super(message);
    }
}
