package com.poscodx.jblog.exception;

public class UserRepositoryException extends RuntimeException{
    public UserRepositoryException() {
        super("UserRepositoryException Occurs");
    }

    public UserRepositoryException(String message) {
        super(message);
    }
}
