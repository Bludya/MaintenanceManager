package org.softuni.maintenancemanager.projects.model.dtos.view;

import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = Project.class, name = "projectViewModel")
public interface ProjectViewModel {

    String getId();

    String getProjectName();

    String getInformation();

    Date getDateCreated();

    Boolean getActive();

    String getManager();
}
