package org.softuni.maintenancemanager.projects.model.dtos.view;


public class ProjectSystemViewModel {

    private String name;

    private String info;

    public ProjectSystemViewModel() {
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
