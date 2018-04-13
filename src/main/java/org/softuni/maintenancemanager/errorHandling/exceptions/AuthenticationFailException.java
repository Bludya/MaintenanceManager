package org.softuni.maintenancemanager.errorHandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationFailException extends RuntimeException{
    private static final String MESSAGE = "Wrong credentials.";
    public AuthenticationFailException() {
        super(MESSAGE);
    }
}
