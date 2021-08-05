package com.fpt.ppmtool.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsExceptionInput extends RuntimeException {

    public UsernameAlreadyExistsExceptionInput(String message) {
        super(message);
    }
    
    
}