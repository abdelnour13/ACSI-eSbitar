package com.example.demo.Details;

import lombok.Data;

@Data
public class IdOrError {
    
    Long id;
    ValidationError err;
    String error;

}
