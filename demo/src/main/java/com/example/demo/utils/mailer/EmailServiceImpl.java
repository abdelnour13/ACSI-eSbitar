package com.example.demo.utils.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("spring.mail.username")
    private String sender;

    @Override
    @Async
    public void sendVerificationEmail(String to,String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@baeldung.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("activate your account !");
        
        mailMessage.setText("click on the link to activate your account :\n"+
            "link expires after one hour !!!\n"+
            "http://localhost:8080/verify?token="+token
        );
        mailSender.send(mailMessage);
    }

    @Override
    @Async
    public void sendRecoverPasswordEmail(String to, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@baeldung.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("activate your account !");
        
        mailMessage.setText("click on the link to change your password :\n"+
            "link expires after one hour !!!\n"+
            "http://localhost:3000/recover-password?token="+token
        );
        mailSender.send(mailMessage);
    }
}