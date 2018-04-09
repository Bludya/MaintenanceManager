package org.softuni.maintenancemanager.errorHandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryNotFound extends RuntimeException {
    private static final String MESSAGE = "Entry does not exist.";

    public EntryNotFound() {
        super(MESSAGE);
    }
}
