package org.softuni.maintenancemanager.projects.model.dtos.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProjectSystemBindModel {
    private static final String EMPTY_NAME = "System name can't be empty";

    @NotNull(message = EMPTY_NAME)
    @NotEmpty(message = EMPTY_NAME)
    private String name;

    private String info;

    public ProjectSystemBindModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
