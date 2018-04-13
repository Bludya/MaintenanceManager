package org.softuni.maintenancemanager.projects.controller;

import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectFullModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectViewModel;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/projects")
public class ProjectController {
    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public ProjectViewModel addProject(@RequestParam ProjectFullModel fullModel,
                                       BindingResult bindingResult,
                                       Authentication auth){
        return this.projectService.addProject(auth.getName(), fullModel);
    }

    @GetMapping("/all")
    public Object allProjects(){
        return this.projectService.getAll();
    }
}
