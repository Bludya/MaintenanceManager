package org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions;

public class ProjectExistsException extends EntryExistsException {
    private static final String NAME = "Project";
    private static final String FIELDS = "name";

    public ProjectExistsException() {
        super(NAME, FIELDS);
    }
}
