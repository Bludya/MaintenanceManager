package org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class UserAlreadyExists extends EntryExistsException {
    private static final String NAME = "User";
    private static final String FIELDS = "username or email";

    public UserAlreadyExists() {
        super(NAME, FIELDS);
    }
}
