package com.example.demo.utils.mailer;

public interface EmailService {
    public void sendVerificationEmail(String to,String token);
    public void sendRecoverPasswordEmail(String to,String token);
}
