package com.example.demo.utils.encryptor;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class EncryptionServiceImpl implements EncryptionService {

    private static final int iterations = 10000;  
    private static final int keylength = 256;  
    public static String charSet = "AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn0123456789";

    @Override
    public String hash(String data, String salt) {
        char[] chars = new char[data.length()];
        PBEKeySpec spec = new PBEKeySpec(
            data.toCharArray(), 
            salt.getBytes(), 
            iterations, 
            keylength
        );  
        Arrays.fill(chars, Character.MIN_VALUE);  
        try {  
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");  
            return Base64.getEncoder().encodeToString(skf.generateSecret(spec).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {  
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);  
        } finally {  
            spec.clearPassword();  
        }  
    }
    @Override
    public boolean compare(String hashedData, String data, String salt) {
        return hashedData.equals(hash(data, salt));
    }
    
    public String getSalt(int salt) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i < salt;i++) {
            builder.append(charSet.charAt(
                (int)(Math.random()*charSet.length())
            ));
        }
        return new String(builder);
    }
}
