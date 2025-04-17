package com.chuckcha.weatherapp.exception;

public class DuplicateLoginException extends RuntimeException {

    public DuplicateLoginException(String message) {
        super(message);
    }
}
