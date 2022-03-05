package com.sowloo.sowloowrita.web.exception;

public class HeadlineDoesNotExistException extends SowlooExceptions {
    public HeadlineDoesNotExistException() {
    }

    public HeadlineDoesNotExistException(String message) {
        super(message);
    }

    public HeadlineDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeadlineDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
