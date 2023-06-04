package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Details.GoogleUserDetails;
import com.example.demo.Details.IdOrError;
import com.example.demo.Details.TokenOrError;
import com.example.demo.Details.UserDetails;
import com.example.demo.Details.ValidationError;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.encryptor.EncryptionService;
import com.example.demo.utils.mailer.EmailService;
import com.example.demo.utils.upload.FileStorageService;
import com.example.demo.utils.validators.UserValidator;
import com.example.demo.utils.validators.Validator;

@RestController
@RequestMapping("/auth")
public class Authentication {
    
    private UserService userService;
    private FileStorageService storageService;
    private EncryptionService encryptionService;
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    public Authentication(UserService userService,
        FileStorageService storageService,
        EncryptionService encryptionService,
        JwtUtils jwtUtils
    ) {
        super();
        this.userService = userService;
        this.storageService = storageService;
        this.encryptionService = encryptionService;
        this.jwtUtils = jwtUtils;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/sign-up")
    public ResponseEntity<IdOrError> signUp(@ModelAttribute UserDetails details) {

        IdOrError idOrError = new IdOrError();

        if(userService.getUserByEmail(details.getEmail()) != null) {
            idOrError.setErr(new ValidationError("email already used", 
                HttpStatus.CONFLICT.value(), "email"));;
            return new ResponseEntity<>(idOrError, HttpStatus.CONFLICT);
        }

        Validator validator = UserValidator.validateUser(details);
        if(!validator.getIsValide()) {
            
            ValidationError error = new ValidationError(
                validator.getMessage(), 
                HttpStatus.BAD_REQUEST.value(), 
                validator.getAttribute()
            );

            idOrError.setErr(error);
            
            return new ResponseEntity<>(idOrError, HttpStatus.BAD_REQUEST);
        }
        
        String filename = null;
        if(details.getPicture() != null) {
            try {
                filename = storageService.save(details.getPicture());
            } catch(Exception e) {}
        }

        User user = details.toUser();
        if(filename != null) user.setPicture("/pictures/"+filename);
        String salt = encryptionService.getSalt(10);
        user.setHashedPassword(
            encryptionService.hash(user.getHashedPassword(), salt)
        );
        user.setSalt(salt);
        user = userService.insertUser(user);
            
        try {
            emailService.sendVerificationEmail(
                user.getEmail(), 
                jwtUtils.generateTokenFromUserId(user.getId())
            );
        } catch(Exception e) {}
        userService.updateUser(user.getId(), user);

        idOrError.setId(user.getId());
        
        return new ResponseEntity<>(idOrError,HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<TokenOrError> login(@RequestBody UserDetails details) {

        String password = details.getHashedPassword();
        String email = details.getEmail();
        TokenOrError tokenOrError = new TokenOrError();

        if(!UserValidator.emailValidator(email).validate()
            || !UserValidator.passwordValidator(password).validate()
        ) {

            tokenOrError.setError("wrong email or password");
            tokenOrError.setType(1);

            return new ResponseEntity<>(
                tokenOrError, 
                HttpStatus.UNAUTHORIZED
            );

        }

        User user = userService.getUserByEmail(email);

        if(user != null && !user.isActive()) {
            
            tokenOrError.setError("account is not verified");
            tokenOrError.setType(2);

            return new ResponseEntity<>(
                tokenOrError, 
                HttpStatus.UNAUTHORIZED
            );

        }

        if(user == null || !encryptionService.compare(
            user.getHashedPassword(), password, user.getSalt())
        ) {
            
            tokenOrError.setError("wrong email or password");
            tokenOrError.setType(1);

            return new ResponseEntity<>(
                tokenOrError, 
                HttpStatus.UNAUTHORIZED
            );

        }

        String token = jwtUtils.generateTokenFromUserId(user.getId());
        
        tokenOrError.setToken(token);
        tokenOrError.setType(0);

        return new ResponseEntity<>(tokenOrError, HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/google/login")
    public ResponseEntity<TokenOrError> googleLogin(@RequestBody GoogleUserDetails details) {
        
        String token = jwtUtils.generateTokenFromUsername(details.getEmail());
        User user = userService.getUserByEmail(details.getEmail());
        TokenOrError tokenOrError = new TokenOrError();
        

        // user already exists
        if(user != null) {
            tokenOrError.setToken(token);
            return new ResponseEntity<>(tokenOrError, HttpStatus.OK);
        }

        Validator validator = UserValidator.validateGoogleUser(details);

        if(!validator.validate()) {
            tokenOrError.setError(
                "invalid attribute attribute"+validator.getAttribute()+"\n"
                +validator.getMessage()
            );
            return new ResponseEntity<>(tokenOrError, HttpStatus.BAD_REQUEST);
        }

        tokenOrError.setToken(token);
        user = userService.insertUser(details.toUser());

        return new ResponseEntity<>(tokenOrError, HttpStatus.CREATED);        
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token
        ,Model model
    ) {

        int result = jwtUtils.validateJwtToken(token);

        if(result != 0) {
            if(result == 1) {
                model.addAttribute(
                    "msg", 
                    "expired link"
                );
            } else {
                model.addAttribute(
                    "msg",
                    "invalid link"
                );
            }
            return "verify";
        }

        Long userId = jwtUtils.getUserIdFromJwtToken(token);
        if(userId == null) {
            model.addAttribute(
                "msg", 
                "invalid link"
            );
            return "verify";
        }

        User user = userService.getUserById(userId);
        if(user == null) {
            model.addAttribute(
                "msg", 
                "invalid link"
            );
            return "verify";
        }
        
        user.setActive(true);
        userService.updateUser(user.getId(), user);
        model.addAttribute(
            "msg", 
            "your account is now verified!"
        );
        return "verify";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/recover")
    public ResponseEntity<ValidationError> recover(@RequestBody UserDetails details) {
        ValidationError error = new ValidationError(null, 0, null);
        if(details.getEmail() == null 
            || !UserValidator.emailValidator(null).validate()
        ) {
            error.setAttribute("email");
            error.setMessage("invalid email adresse");
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        String token = jwtUtils.generateTokenFromUsername("pass$"+details.getEmail());
        emailService.sendRecoverPasswordEmail(details.getEmail(), token);
        return null;
    }

}