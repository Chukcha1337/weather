package com.chuckcha.weatherapp.exception;

public class SessionTimeoutException extends RuntimeException {
    public SessionTimeoutException(String message) {
        super(message);
    }
}
