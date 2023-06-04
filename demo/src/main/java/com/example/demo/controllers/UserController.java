package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Details.UserOrError;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtils;

@RestController
@RequestMapping("/users")
public class UserController {

    private JwtUtils jwtUtils;
    private UserService userService;

    public UserController(JwtUtils jwtUtils,UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<UserOrError> getUserById(
        @PathVariable(name = "id") Long id,
        @RequestHeader(name = "Authorization") String token
    ) {

        UserOrError userOrError = new UserOrError();
        int result = 0;

        if(token == null || (result=jwtUtils.validateJwtToken(token)) != 0) {

            System.out.println("unautherized bitch");

            if(result == 1) {
                userOrError.setError("expired token");
            } else {
                userOrError.setError("invalid token");
            }

            return new ResponseEntity<>(userOrError, HttpStatus.UNAUTHORIZED);

        }

        Long _id = jwtUtils.getUserIdFromJwtToken(token);

        if(!_id.equals(id)) {

            userOrError.setError("not allowed to read this user's data");
            return new ResponseEntity<>(userOrError, HttpStatus.UNAUTHORIZED);

        }

        User user = userService.getUserById(_id);

        if(user == null) {

            userOrError.setError("user not found");
            return new ResponseEntity<>(userOrError, HttpStatus.NOT_FOUND);

        }

        userOrError.setUser(user);
        return new ResponseEntity<>(userOrError, HttpStatus.OK);


    }

}
