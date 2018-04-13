package org.softuni.maintenancemanager.projects.controller;

import org.softuni.maintenancemanager.errorHandling.exceptions.BindingModelException;
import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectSystemBindModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectSystemViewModel;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectSystemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/project-systems")
public class ProjectSystemController {
    private ProjectSystemsService projectSystemsService;

    @Autowired
    public ProjectSystemController(ProjectSystemsService projectSystemsService) {
        this.projectSystemsService = projectSystemsService;
    }

    @PostMapping("/add")
    public ProjectSystemViewModel addProjectSystem(@RequestParam ProjectSystemBindModel bindModel,
                                                   BindingResult bindingResult,
                                                   Authentication auth){
        if(bindingResult.hasErrors()){
            StringBuilder errorText = new StringBuilder("");

            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errorText.append(objectError.getObjectName()).append(": ").append(objectError.getDefaultMessage());
            }
            throw new BindingModelException(errorText.toString());
        }

        return this.projectSystemsService.addSystem(auth.getName(),bindModel);
    }

    @GetMapping("/all")
    public Object getAllProjectSystems(){
        return this.projectSystemsService.getAll();
    }
}
