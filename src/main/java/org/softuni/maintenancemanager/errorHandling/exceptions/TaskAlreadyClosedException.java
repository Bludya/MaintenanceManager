package org.softuni.maintenancemanager.errorHandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class TaskAlreadyClosedException extends RuntimeException {
    private static final String MESSAGE = "Task is already closed.";

    public TaskAlreadyClosedException() {
        super(MESSAGE);
    }
}
