package com.example.demo.Details;

import lombok.Data;

@Data
public class ValidationError {

    public String message;
    public int status;
    public String attribute;

    public ValidationError(String message,int status,String attribute) {
        this.message = message;
        this.status = status;
        this.attribute = attribute;
    }
}
