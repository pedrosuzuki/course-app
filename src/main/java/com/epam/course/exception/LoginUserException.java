package com.epam.course.exception;

public class LoginUserException extends RuntimeException {
    public LoginUserException(String message) {
        super(message);
    }
}
