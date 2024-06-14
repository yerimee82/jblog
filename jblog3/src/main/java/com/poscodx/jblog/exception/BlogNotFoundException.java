package com.poscodx.jblog.exception;

public class BlogNotFoundException extends RuntimeException {
    public BlogNotFoundException() {
        super("Blog Not Found Exception Occurs");
    }

    public BlogNotFoundException(String message) {
        super(message);
    }
}
