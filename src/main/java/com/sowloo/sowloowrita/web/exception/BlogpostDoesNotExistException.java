package com.sowloo.sowloowrita.web.exception;

public class BlogpostDoesNotExistException extends SowlooExceptions {
    public BlogpostDoesNotExistException() {
    }

    public BlogpostDoesNotExistException(String message) {
        super(message);
    }

    public BlogpostDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogpostDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
