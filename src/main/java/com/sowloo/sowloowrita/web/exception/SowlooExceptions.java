package com.sowloo.sowloowrita.web.exception;

public class SowlooExceptions extends RuntimeException{
    public SowlooExceptions() {
    }

    public SowlooExceptions(String message) {
        super(message);
    }

    public SowlooExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public SowlooExceptions(Throwable cause) {
        super(cause);
    }
}
