package com.example.demo.Details;

import com.example.demo.models.User;

import lombok.Data;

@Data
public class UserOrError {
    
    private User user;
    private String error;
    
}
