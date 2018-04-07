package org.softuni.maintenancemanager.projects.model.dtos.view;

import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Project.class, name = "projectViewModel")
public class ProjectViewModel {

}
