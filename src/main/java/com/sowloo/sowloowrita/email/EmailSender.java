package com.sowloo.sowloowrita.email;

public interface EmailSender {

    void send (String to, String email);

    void send(String toAddress, String subject, String body);
}
