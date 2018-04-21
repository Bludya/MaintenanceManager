package org.softuni.maintenancemanager.projects.service.interfaces;

import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectFullModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectViewModel;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public interface ProjectService {

    Set<ProjectViewModel> getAll();

    ProjectViewModel getByName(String name);

    ProjectViewModel addProject(String author, ProjectFullModel projectBindModel, Set<String> systems);

    ProjectViewModel changeProjectActive(String author, String projectName, String action);

    @Transactional
    Long deleteProject(String userName, String projectName);

    Project getProjectByProjectName(String projectName);
}
