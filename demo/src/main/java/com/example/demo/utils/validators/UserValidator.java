package com.example.demo.utils.validators;

import com.example.demo.Details.GoogleUserDetails;
import com.example.demo.Details.UserDetails;

public class UserValidator {

    public static Validator validateUser(UserDetails user) {
        Validator validator = new Validator();
        validator
            .addValidator(UserValidator.emailValidator(user.getEmail()))
            .attribute("name", user.getName())
                .notNull()
                .minLength(1)
                .maxLength(255)
            .attribute("re-password", user.getRePassword())
                .in("confirm your password again", user.getHashedPassword())
            .addValidator(UserValidator.passwordValidator(user.getHashedPassword()))
                .attribute("gender", user.getGender())
                .notNull()
                .in("gender can be either male or female","unkown", "male","female")
            .validate();
        return validator;
    }

    public static Validator passwordValidator(String password) {
        Validator validator = new Validator();
        return validator
            .attribute("password", password)
                .notNull()
                .minLength(8)
                .contains(Validator.caps, 
                    "password must contain at least one capital letter"
                )
                .contains(Validator.mins,
                    "password must contain at least one non-capital letter"
                )
                .contains(Validator.nums,
                    "password must contain at least one number"
                );
    }

    public static Validator emailValidator(String email) {
        Validator validator = new Validator();
        return validator
            .attribute("email", email)
                .notNull()
                .isEmail();
                
    }

    public static Validator validateGoogleUser(GoogleUserDetails user) {
        Validator validator = new Validator();
        validator
            .addValidator(UserValidator.emailValidator(user.getEmail()))
            .attribute("name", user.getName())
                .notNull()
                .notEmpty()
                .maxLength(255)
            .attribute("gender", user.getGender())
                .notNull()
                .in("gender can be either male or female","unkown", "male","female")
            .validate();
        return validator;
    }

}
