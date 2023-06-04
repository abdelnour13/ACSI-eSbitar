package com.example.demo.utils.encryptor;

public interface EncryptionService {
    public String hash(String data, String salt);
    public boolean compare(String hashedData, String data,String salt);
    public String getSalt(int salt);
}
