package org.softuni.maintenancemanager.projects.controller;

import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectFullModel;
import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectViewModel;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "authorization", exposedHeaders = "authorization")
@RequestMapping("/projects")
public class ProjectController {
    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public ProjectViewModel addProject(@Valid @ModelAttribute ProjectFullModel fullModel,
                                       @RequestParam(name = "systems[]", required = false) Set<String> systems,
                                       @RequestParam(name = "systems", required = false) String system,
                                       BindingResult bindingResult,
                                       Authentication auth){

        if(systems == null){
            systems = new HashSet<>();
            systems.add(system);
        }
        return this.projectService.addProject(auth.getName(), fullModel, systems);
    }

    @GetMapping("/all")
    public Set<ProjectViewModel> allProjects(){
        return this.projectService.getAll();
    }

    @GetMapping("/active/all")
    public Set<ProjectViewModel> activeProjects(){
        return this.projectService.getActive();
    }

    @GetMapping("/{projectName}")
    public ProjectViewModel getProject(@PathVariable String projectName){
        return this.projectService.getByName(projectName);
    }

    @PostMapping("/action/{action}")
    public ProjectViewModel activateProject(@PathVariable String action,
                                            @RequestParam(required = false) String projectName,
                                            Authentication auth){
        return this.projectService.changeProjectActive(auth.getName(), projectName, action);
    }

    @PostMapping("/delete")
    public Long activateProject(@RequestParam String projectName,
                                            Authentication auth){
        return this.projectService.deleteProject(auth.getName(), projectName);
    }
}
