package org.softuni.maintenancemanager.tasks.model.dtos.binding;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TaskBindModel {

    private String info;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private String projectName;

    public TaskBindModel() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String information) {
        this.info = information;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
