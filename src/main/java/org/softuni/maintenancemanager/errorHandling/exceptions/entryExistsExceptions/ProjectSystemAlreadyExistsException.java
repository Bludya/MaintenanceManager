package org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions;

public class ProjectSystemAlreadyExistsException extends EntryExistsException{
    private static final String NAME = "System";
    private static final String FIELDS = "name";

    public ProjectSystemAlreadyExistsException() {
        super(NAME, FIELDS);
    }
}
