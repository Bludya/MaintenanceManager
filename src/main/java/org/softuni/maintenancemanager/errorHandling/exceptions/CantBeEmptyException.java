package org.softuni.maintenancemanager.errorHandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CantBeEmptyException extends RuntimeException{
    private static final String MESSAGE = "%s text can't be empty.";

    public CantBeEmptyException(String field) {
        super(String.format(MESSAGE, field));
    }
}
