package com.chuckcha.weatherapp.exception;

public class InvalidSessionIdException extends RuntimeException {
    public InvalidSessionIdException(String message) {
        super(message);
    }
}
