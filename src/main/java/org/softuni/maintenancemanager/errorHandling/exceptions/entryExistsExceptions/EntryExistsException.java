package org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class EntryExistsException extends RuntimeException {
    private static final String MESSAGE = "%s with the same %s already exists.";

    public EntryExistsException(String name, String fields) {
        super(String.format(MESSAGE, name, fields));
    }
}
