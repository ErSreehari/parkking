package com.parkking.parkingservice.exception;

public class InternalServerErrorException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(String message) {
        super(message);
    }
}
