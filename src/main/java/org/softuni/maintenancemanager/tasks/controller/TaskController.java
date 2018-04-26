package org.softuni.maintenancemanager.tasks.controller;

import org.softuni.maintenancemanager.tasks.model.dtos.binding.TaskBindModel;
import org.softuni.maintenancemanager.tasks.model.dtos.view.TaskBasicViewModel;
import org.softuni.maintenancemanager.tasks.model.dtos.view.TaskViewModel;
import org.softuni.maintenancemanager.tasks.service.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "authorization", exposedHeaders = "authorization")
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public Object addTask(@Valid @ModelAttribute TaskBindModel taskBindModel,
                                 @RequestParam(name = "employees[]", required = false) Set<String> employees,
                                 @RequestParam(name = "employee", required = false) String employee,
                                 Authentication auth){

        if(employees == null){
            employees = new HashSet<>();
            employees.add(employee);
        }

        return this.taskService.addTask(auth.getName(), taskBindModel, employees);
    }

    @GetMapping("/completed/{completed}")
    public Set<TaskBasicViewModel> getByCompleted(@PathVariable Boolean completed){
        return this.taskService.getByCompleted(completed);
    }

    @GetMapping("/my")
    public Set<TaskBasicViewModel> getMyTasks(Authentication auth){
        return this.taskService.getUsersTasks(auth.getName());
    }

    @GetMapping("/get/{id}")
    public TaskViewModel getOneTask(@PathVariable Long id){
        return this.taskService.getTaskById(id);
    }

    @PostMapping("/finish")
    public TaskViewModel finishTask(@RequestParam Long id,
                                    @RequestParam String closingNote,
                                    Authentication auth){
        return this.taskService.finishTask(auth.getName(), id, closingNote);
    }

    @PostMapping("/addNote")
    public TaskViewModel addNote(@RequestParam Long id,
                                 @RequestParam String note,
                                 Authentication auth){
        return this.taskService.addNote(auth.getName(), id, note);
    }
}
