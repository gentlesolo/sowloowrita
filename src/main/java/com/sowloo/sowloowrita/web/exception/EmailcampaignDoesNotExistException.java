package com.sowloo.sowloowrita.web.exception;

public class EmailcampaignDoesNotExistException extends SowlooExceptions {
    public EmailcampaignDoesNotExistException() {
    }

    public EmailcampaignDoesNotExistException(String message) {
        super(message);
    }

    public EmailcampaignDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailcampaignDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
