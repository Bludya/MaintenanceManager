package org.softuni.maintenancemanager.projects.model.dtos.view;

import org.softuni.maintenancemanager.projects.model.entity.ProjectSystem;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = ProjectSystem.class, name = "projectSystemViewModel")
public interface ProjectSystemViewModel {

    String getName();

    String getInfo();
}
