package com.example.demo.Details;

import com.example.demo.models.User;

import lombok.Data;

@Data
public class GoogleUserDetails {
    
    private String email;
    private String name;
    private String picture;
    private String gender;
    private boolean active;

    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPicture(picture);
        user.setGender(gender);
        user.setProvider(User.Provider.GOOGLE);
        user.setActive(active);
        return user;
    }

}
