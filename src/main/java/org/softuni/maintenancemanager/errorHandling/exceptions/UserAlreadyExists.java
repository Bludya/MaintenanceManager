package org.softuni.maintenancemanager.errorHandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class UserAlreadyExists extends RuntimeException {
    private static final String MESSAGE = "User with the same username or email already exists.";

    public UserAlreadyExists() {
        super(MESSAGE);
    }
}
