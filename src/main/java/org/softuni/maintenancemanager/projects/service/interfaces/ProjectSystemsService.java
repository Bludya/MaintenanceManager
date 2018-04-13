package org.softuni.maintenancemanager.projects.service.interfaces;

import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectSystemBindModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectSystemViewModel;
import org.softuni.maintenancemanager.projects.model.entity.ProjectSystem;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ProjectSystemsService {

    Set<ProjectSystemViewModel> getAll();

    ProjectSystemViewModel addSystem(String author, ProjectSystemBindModel projectSystemBindModel);

    Set<ProjectSystem> getAllContainedIn(Set<String> systemNames);
}
