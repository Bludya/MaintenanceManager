package org.softuni.maintenancemanager.projects.model.dtos.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProjectSystemBindModel {
    private static final String EMPTY_NAME = "System name can't be empty";

    @NotNull(message = EMPTY_NAME)
    @NotEmpty(message = EMPTY_NAME)
    public String name;

    public String info;
}
