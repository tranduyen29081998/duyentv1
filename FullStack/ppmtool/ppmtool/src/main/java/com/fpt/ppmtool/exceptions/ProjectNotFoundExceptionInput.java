package com.fpt.ppmtool.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundExceptionInput extends RuntimeException {

    public ProjectNotFoundExceptionInput(String message) {
        super(message);
    }
}