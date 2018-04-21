package org.softuni.maintenancemanager.tasks.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.logger.service.interfaces.Logger;
import org.softuni.maintenancemanager.projects.service.interfaces.ProjectService;
import org.softuni.maintenancemanager.tasks.model.dtos.binding.TaskBindModel;
import org.softuni.maintenancemanager.tasks.model.dtos.view.TaskBasicViewModel;
import org.softuni.maintenancemanager.tasks.model.dtos.view.TaskViewModel;
import org.softuni.maintenancemanager.tasks.model.entity.Task;
import org.softuni.maintenancemanager.tasks.model.repositories.TaskRepository;
import org.softuni.maintenancemanager.tasks.service.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{
    private TaskRepository repository;
    private UserService userService;
    private ProjectService projectService;
    private Logger logger;
    private ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository repository,
                           UserService userService,
                           ProjectService projectService,
                           Logger logger,
                           ModelMapper modelMapper) {
        this.repository = repository;
        this.userService = userService;
        this.projectService =projectService;
        this.logger = logger;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskViewModel addTask(String user, TaskBindModel taskDto, Set<String> employees) {
        Task task = this.modelMapper.map(taskDto, Task.class);
        task.setAuthor(this.userService.getUserByUsername(user));
        task.setProject(this.projectService.getProjectByProjectName(taskDto.getProjectName()));
        task.setAssignedWorkers(this.userService.getUsersByUsernames(employees));
        task.setCompleted(false);
        task.setDateCreated(LocalDate.now());
        this.repository.save(task);
        this.logger.addLog(user, "Added task with id:" + task.getId());
        return null;
    }

    @Override
    public Set<TaskBasicViewModel> getUsersTasks(String username) {
        return this.repository.getForUser(username)
                .stream()
                .map(task -> this.modelMapper.map(task, TaskBasicViewModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TaskBasicViewModel> getByCompleted(Boolean completed) {
        return this.repository.getByCompleted(completed)
                .stream()
                .map(task -> this.modelMapper.map(task, TaskBasicViewModel.class))
                .collect(Collectors.toSet());
    }
}
