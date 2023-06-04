package com.example.demo.controllers;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Details.GoogleUserDetails;
import com.example.demo.Details.UserDetails;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.encryptor.EncryptionService;
import com.example.demo.utils.mailer.EmailService;
import com.example.demo.utils.upload.FileStorageService;
import com.example.demo.utils.validators.UserValidator;
import com.example.demo.utils.validators.Validator;

@Controller
@RequestMapping("/")
public class Auth {

    private UserService userService;
    private FileStorageService storageService;
    private EncryptionService encryptionService;
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    public Auth(UserService userService,
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

    @GetMapping("/")
    public String main() {
        return "redirect:/login";
    }

    @PostMapping("/google/login")
    public ResponseEntity<String> googleLogin(@ModelAttribute GoogleUserDetails details,
        HttpServletResponse response
    ) {
        String token = jwtUtils.generateTokenFromUsername(details.getEmail());
        User user = userService.getUserByEmail(details.getEmail());

        // user already exists
        if(user != null) {
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }

        user = userService.insertUser(details.toUser());

        return new ResponseEntity<String>(token, HttpStatus.CREATED);        
    }

    @GetMapping("/login")
    public String getLogin(
        @CookieValue(name = "auth", defaultValue = "") String auth,
        Model model
    ) {
        if(jwtUtils.validateJwtToken(auth) == 0) {
            String email = jwtUtils.getUserNameFromJwtToken(auth);
            User user = userService.getUserByEmail(email);
            model.addAttribute("url", user.getPicture());
            return "home";
        }
        model.addAttribute("msg", "Hello");
        model.addAttribute("type", "NO_ERR");
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(HttpServletResponse response,
        @ModelAttribute UserDetails details,
        Model model
    ) {
        String password = details.getHashedPassword();
        String email = details.getEmail();

        if(!UserValidator.emailValidator(email).validate()
            || !UserValidator.passwordValidator(password).validate()
        ) {
            model.addAttribute("type", "WRNG_CRED");
            model.addAttribute("msg", 
                "wrong email or password"
            );
            model.addAttribute("user", details);
            return "login";        
        }

        User user = userService.getUserByEmail(email);

        if(user != null && !user.isActive()) {
            model.addAttribute("type", "NOT_ACTIVE");
            model.addAttribute("msg", 
                "you need to activate your account"
            );
            model.addAttribute("user", details);
            return "login";
        }

        if(user == null || !encryptionService.compare(
            user.getHashedPassword(), password, user.getSalt())
        ) {
            model.addAttribute("type", "WRNG_CRED");
            model.addAttribute("msg", 
                "wrong email or password"
            );
            model.addAttribute("user", details);
            return "login";
        }

        String cookie = jwtUtils.generateTokenFromUsername(details.getEmail());
        response.addCookie(new Cookie("auth", cookie));
        model.addAttribute("url", user.getPicture());
        return "home";
    } 

    @GetMapping("/signup")
    public String getSignup(
        @CookieValue(name = "auth", defaultValue = "") String auth,
        Model model
    ) {
        if(jwtUtils.validateJwtToken(auth) == 0) {
            String email = jwtUtils.getUserNameFromJwtToken(auth);
            User user = userService.getUserByEmail(email);
            model.addAttribute("url", user.getPicture());
            return "home";
        }
        model.addAttribute("msg", "");
        model.addAttribute("att", "");
        model.addAttribute("user", new UserDetails());
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute UserDetails details
        ,Model model
    ) {
        if(userService.getUserByEmail(details.getEmail()) != null) {
            model.addAttribute("msg", "email already used");
            model.addAttribute("att", "email");
            model.addAttribute("user", details);

            return "signup";
        }
        Validator validator = UserValidator.validateUser(details);
        if(validator.getIsValide()) {
            
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
            return "redirect:/login";
        }
        model.addAttribute("msg", validator.getMessage());
        model.addAttribute("att", validator.getAttribute());
        model.addAttribute("user", details);

        return "signup";
    } 

    @GetMapping("/home")
    public String home(
        @CookieValue(name = "auth", defaultValue = "") String auth,
        Model model
    ) {
        if(jwtUtils.validateJwtToken(auth) == 0) {
            String email = jwtUtils.getUserNameFromJwtToken(auth);
            User user = userService.getUserByEmail(email);
            model.addAttribute("url", user.getPicture());
            return "home";
        }
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(
        HttpServletResponse response,
        Model model
    ) {
        response.addCookie(new Cookie("auth", ""));
        return "redirect:/login";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token
        ,Model model
    ) {
        int result = jwtUtils.validateJwtToken(token);
        if(result != 0) {
            if(result == 1) {
                model.addAttribute("msg", "expired link");
            } else {
                model.addAttribute("msg", "invalid link");
            }
            return "verify";
        }

        Long userId = jwtUtils.getUserIdFromJwtToken(token);
        if(userId == null) {
            model.addAttribute("msg", "invalid link");
            return "verify";
        }

        User user = userService.getUserById(userId);
        if(user == null) {
            model.addAttribute("msg", "invalid link");
            return "verify";
        }
        
        user.setActive(true);
        userService.updateUser(user.getId(), user);
        model.addAttribute("msg", "your account is now verified!");
        return "verify";
    }

}