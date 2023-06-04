package com.example.demo.utils.upload;



import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

public interface FileStorageService {
    public void init();
    public String save(MultipartFile file);
    public Resource load(String filename);
}
