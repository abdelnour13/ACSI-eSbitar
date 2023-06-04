package com.example.demo.Details;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.User;

import lombok.Data;

@Data
public class UserDetails {

    private String name;
    private String email;
    private String hashedPassword;
    private String gender;
    private String googleId;
    private MultipartFile picture;
    private String rePassword;

    public User toUser() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(hashedPassword);
        user.setGender(gender);
        user.setGoogleId(googleId);
        user.setProvider(User.Provider.LOCAL);
        return user;
    }
    
}
