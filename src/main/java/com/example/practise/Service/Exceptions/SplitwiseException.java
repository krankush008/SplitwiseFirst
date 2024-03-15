package com.example.practise.Service.Exceptions;

public class SplitwiseException extends RuntimeException {
    public SplitwiseException(String message) {
        super(message);
    }
    public SplitwiseException(String message, Throwable cause) {
        super(message, cause);
    }
}
