package org.softuni.maintenancemanager.projects.service.interfaces;

import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectFullModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectViewModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ProjectService {

    Set<ProjectViewModel> getAll();

    ProjectViewModel addProject(String author, ProjectFullModel projectBindModel);
}
