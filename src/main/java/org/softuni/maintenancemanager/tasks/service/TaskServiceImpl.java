package org.softuni.maintenancemanager.tasks.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.appUtils.CharacterEscapes;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.errorHandling.exceptions.CantBeEmptyException;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFoundException;
import org.softuni.maintenancemanager.errorHandling.exceptions.TaskAlreadyClosedException;
import org.softuni.maintenancemanager.logger.service.interfaces.Logger;
import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;
import org.softuni.maintenancemanager.notes.model.entity.Note;
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
import java.util.Optional;
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
        try {
            taskDto = CharacterEscapes.escapeStringFields(taskDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        employees = employees.stream().map(CharacterEscapes::escapeString).collect(Collectors.toSet());

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
        username = CharacterEscapes.escapeString(username);

        return this.repository.getForUser(username)
                .stream()
                .map(task -> this.modelMapper.map(task, TaskBasicViewModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TaskBasicViewModel> getByCompleted(Boolean completed) {
        return this.repository.getByCompleted(completed)
                .stream()
                .map(task -> {
                    TaskBasicViewModel taskBasicViewModel = this.modelMapper.map(task, TaskBasicViewModel.class);
                    taskBasicViewModel.setProjectName(task.getProject().getProjectName());
                    return taskBasicViewModel;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public TaskViewModel getTaskById(Long id) {
        return this.mapTaskToViewModel(this.getTask(id));
    }

    @Override
    public TaskViewModel finishTask(String username, Long id, String closingNote) {
        closingNote = CharacterEscapes.escapeString(closingNote);

        Task task = this.getTask(id);

        if(task.getCompleted()){
            throw new TaskAlreadyClosedException();
        }

        if(closingNote == null || closingNote.equals("")){
            throw new CantBeEmptyException("Closing note");
        }

        task.setCompleted(true);
        task.setCompletionDate(LocalDate.now());
        task.setCompletionNote(closingNote);
        task.setUserCompleted(this.userService.getUserByUsername(username));

        this.repository.save(task);
        this.logger.addLog(username, "Closed task with id: " + task.getId());

        return this.mapTaskToViewModel(task);
    }

    @Override
    public TaskViewModel addNote(String username, Long id, String noteText) {
        noteText = CharacterEscapes.escapeString(noteText);

        if(noteText == null || noteText.equals("")){
            throw new CantBeEmptyException("Note");
        }


        Task task = this.getTask(id);

        Note note = new Note();
        note.setText(noteText);
        note.setAuthor(this.userService.getUserByUsername(username));
        note.setDateWritten(LocalDate.now());

        task.addNote(note);

        this.repository.save(task);
        return this.mapTaskToViewModel(task);
    }

    private TaskViewModel mapTaskToViewModel(Task task) {

        TaskViewModel taskViewModel = this.modelMapper.map(task, TaskViewModel.class);
        taskViewModel.setAssignedWorkers(
                task.getAssignedWorkers()
                        .stream()
                        .map(User::getUsername)
                        .collect(Collectors.toSet()));
        taskViewModel.setProjectName(task.getProject().getProjectName());
        taskViewModel.setAuthor(task.getAuthor().getUsername());
        try {

            taskViewModel.setUserCompleted(task.getUserCompleted().getUsername());
            taskViewModel.setTicketId(task.getTicket().getId());
            taskViewModel.setNotes(
                    task.getNotes().stream()
                        .map(note ->{
                            NoteViewModel nvm = this.modelMapper.map(note, NoteViewModel.class);
                            nvm.setAuthor(note.getAuthor().getUsername());
                            return nvm;
                        })
                        .collect(Collectors.toSet())
            );
        }catch (NullPointerException npe){
            taskViewModel.setTicketId(null);
        }
        return taskViewModel;
    }

    private Task getTask(Long id){
        Optional<Task> optionalTask = this.repository.findById(id);

        if(!optionalTask.isPresent()){
            throw new EntryNotFoundException();
        }

        return optionalTask.get();
    }
}
