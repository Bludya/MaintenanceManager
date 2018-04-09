package org.softuni.maintenancemanager.errorHandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class EntryCanNotBeCreated extends RuntimeException {
    private static final String MESSAGE = "Invalid request. The following parameters are invalid: ";

    public EntryCanNotBeCreated(String... fields) {
        super(MESSAGE + fields);
    }
}
