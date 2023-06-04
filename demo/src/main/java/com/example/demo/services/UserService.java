package com.example.demo.services;

import java.util.List;

import com.example.demo.models.User;

public interface UserService {
    User insertUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User updateUser(Long id,User user);
}
