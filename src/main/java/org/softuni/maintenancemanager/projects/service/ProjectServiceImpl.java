package org.softuni.maintenancemanager.projects.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions.ProjectExistsException;
import org.softuni.maintenancemanager.logger.service.interfaces.LogService;
import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectFullModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectViewModel;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.softuni.maintenancemanager.projects.model.entity.ProjectSystem;
import org.softuni.maintenancemanager.projects.model.repositories.ProjectRepository;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectService;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectSystemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private LogService logService;
    private UserService userService;
    private ProjectSystemsService projectSystemsService;
    private ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              LogService logService,
                              ProjectSystemsService projectSystemsService,
                              ModelMapper modelMapper,
                              UserService userService) {
        this.projectRepository = projectRepository;
        this.logService = logService;
        this.modelMapper = modelMapper;
        this.projectSystemsService = projectSystemsService;
        this.userService = userService;
    }

    @Override
    public Set<ProjectViewModel> getAll() {
        return this.projectRepository
                .findAll()
                .stream()
                .map(project -> {
                    ProjectViewModel pvm = modelMapper.map(project, ProjectViewModel.class);
                    if(project.getManager() != null) {
                        pvm.setManager(project.getManager().getUsername());
                    }
                    if(project.getSystems() != null) {
                        pvm.setSystems(project.getSystems().stream()
                                .map(ProjectSystem::getName)
                                .collect(Collectors.toSet()));
                    }
                    return pvm;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public ProjectViewModel addProject(String author, ProjectFullModel projectBindModel) {
        if(this.projectRepository.existsByProjectName(projectBindModel.projectName)){
            throw new ProjectExistsException();
        }

        Project project = this.modelMapper.map(modelMapper, Project.class);
        project.setActive(true);
        project.setAuthor(this.userService.getUserByUsername(author));
        project.setSystems(this.projectSystemsService.getAllContainedIn(projectBindModel.systems));

        this.projectRepository.save(project);
        this.logService.addLog(author, "Added new project: " + projectBindModel.projectName);

        return this.modelMapper.map(project, ProjectViewModel.class);
    }
}
