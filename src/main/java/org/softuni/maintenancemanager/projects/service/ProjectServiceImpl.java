package org.softuni.maintenancemanager.projects.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.appUtils.CharacterEscapes;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFoundException;
import org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions.ProjectExistsException;
import org.softuni.maintenancemanager.logger.service.interfaces.Logger;
import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectFullModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectViewModel;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.softuni.maintenancemanager.projects.model.entity.ProjectSystem;
import org.softuni.maintenancemanager.projects.model.repositories.ProjectRepository;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectService;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectSystemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private Logger logger;
    private UserService userService;
    private ProjectSystemsService projectSystemsService;
    private ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              Logger logger,
                              ProjectSystemsService projectSystemsService,
                              ModelMapper modelMapper,
                              UserService userService) {
        this.projectRepository = projectRepository;
        this.logger = logger;
        this.modelMapper = modelMapper;
        this.projectSystemsService = projectSystemsService;
        this.userService = userService;
    }

    @Override
    public Set<ProjectViewModel> getAll() {
        return this.projectRepository
                .getAllByOrderByProjectNameDesc()
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
    public ProjectViewModel getByName(String name) {
        name = CharacterEscapes.escapeString(name);

        Project project = this.projectRepository.getByProjectName(name);

        if(project == null){
            throw new EntryNotFoundException();
        }

        return this.mapProjectToView(project);
    }

    @Override
    public ProjectViewModel addProject(String author, ProjectFullModel projectBindModel, Set<String> systems) {

        try {
            projectBindModel = CharacterEscapes.escapeStringFields(projectBindModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        systems = systems.stream().map(CharacterEscapes::escapeString).collect(Collectors.toSet());

        if(this.projectRepository.existsByProjectName(projectBindModel.getProjectName())){
            throw new ProjectExistsException();
        }

        Project project = this.mapDtoToProject(projectBindModel, systems);
        project.setAuthor(this.userService.getUserByUsername(author));
        project.setDateCreated(new Date());
        this.projectRepository.save(project);
        this.logger.addLog(author, "Added new project: " + projectBindModel.getProjectName());

        return this.mapProjectToView(project);
    }

    @Override
    public ProjectViewModel changeProjectActive(String author, String projectName, String action) {
        action = CharacterEscapes.escapeString(action);
        projectName = CharacterEscapes.escapeString(projectName);

        Project project = this.projectRepository.getByProjectName(projectName);

        if(project == null){
            throw new EntryNotFoundException();
        }

        if(action.equals("activate")){
            project.setActive(true);
        }
        else if(action.equals("deactivate")){
            project.setActive(false);
        }

        this.projectRepository.save(project);

        action = action.substring(0, 1).toUpperCase() + action.substring(1);
        this.logger.addLog(author, action + "d project " + projectName);

        return this.mapProjectToView(project);
    }

    @Override
    @Transactional
    public Long deleteProject(String userName, String projectName){
        projectName = CharacterEscapes.escapeString(projectName);

        Long deletedRows = this.projectRepository.deleteByProjectName(projectName);
        this.logger.addLog(userName, "Deleted project with name: " + projectName);

        return deletedRows;
    }

    @Override
    public Project getProjectByProjectName(String projectName) {
        projectName = CharacterEscapes.escapeString(projectName);

        return this.projectRepository.getByProjectName(projectName);
    }

    private ProjectViewModel mapProjectToView(Project project){
        ProjectViewModel pvm = modelMapper.map(project, ProjectViewModel.class);
        pvm.setSystems(
                project.getSystems()
                        .stream()
                        .map(ProjectSystem::getName)
                        .collect(Collectors.toSet()));

        if(pvm.getManager() == null){
            pvm.setManager("No manager currently.");
        } else {
            pvm.setManager(project.getManager().getUsername());
        }

        return pvm;
    }

    private Project mapDtoToProject(ProjectFullModel projectBindModel, Set<String> systems){
        Project project = this.modelMapper.map(projectBindModel, Project.class);
        project.setActive(true);
        project.setManager(this.userService.getUserByUsername(projectBindModel.getManager()));
        project.setSystems(this.projectSystemsService.getAllContainedIn(systems));

        return project;
    }
}
