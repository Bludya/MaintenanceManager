package org.softuni.maintenancemanager.projects.model.dtos.binding;

public class ProjectFullModel {

    private String projectName;

    private String information;

    private String manager;

    public ProjectFullModel() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
