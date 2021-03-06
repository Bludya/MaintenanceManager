package org.softuni.maintenancemanager.projects.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.appUtils.CharacterEscapes;
import org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions.ProjectSystemAlreadyExistsException;
import org.softuni.maintenancemanager.logger.service.interfaces.Logger;
import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectSystemBindModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectSystemViewModel;
import org.softuni.maintenancemanager.projects.model.entity.ProjectSystem;
import org.softuni.maintenancemanager.projects.model.repositories.ProjectSystemRepository;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectSystemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectSystemsServiceImpl implements ProjectSystemsService{

    private ProjectSystemRepository repository;
    private ModelMapper modelMapper;
    private Logger logger;

    @Autowired
    public ProjectSystemsServiceImpl(ProjectSystemRepository repository,
                                     ModelMapper modelMapper,
                                     Logger logger) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.logger = logger;
    }

    @Override
    public ProjectSystemViewModel addSystem(String author, ProjectSystemBindModel projectSystemBindModel) {
        try {
            projectSystemBindModel = CharacterEscapes.escapeStringFields(projectSystemBindModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(this.repository.existsByName(projectSystemBindModel.getName())){
            throw new ProjectSystemAlreadyExistsException();
        }

        ProjectSystem projectSystem = modelMapper.map(projectSystemBindModel, ProjectSystem.class);
        this.repository.save(projectSystem);
        this.logger.addLog(author, "Added a new system with the name: " + projectSystem.getName());

        return modelMapper.map(projectSystem, ProjectSystemViewModel.class);
    }

    @Override
    public Set<ProjectSystem> getAllContainedIn(Set<String> systemNames) {
        systemNames = systemNames.stream().map(CharacterEscapes::escapeString).collect(Collectors.toSet());

        return this.repository.findAllByNameIn(systemNames);
    }


    @Override
    public Set<ProjectSystemViewModel> getAll() {
        return this.repository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, ProjectSystemViewModel.class))
                .collect(Collectors.toSet());
    }

}
