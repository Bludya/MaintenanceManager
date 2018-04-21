package org.softuni.maintenancemanager.tasks.model.dtos.view;

import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.softuni.maintenancemanager.tasks.model.entity.Task;
import org.softuni.maintenancemanager.tickets.model.entity.Ticket;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.Set;

@Projection(types = Task.class, name = "taskViewModel")
public class TaskViewModel {
    private Long id;

    private String info;

    protected LocalDate dateCreated;

    private LocalDate deadline;

    private Boolean completed;

    private Set<String> assignedWorkers;

    private Project project;

    private Ticket ticket;

    private Set<NoteViewModel> notes;

    public TaskViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Set<String> getAssignedWorkers() {
        return assignedWorkers;
    }

    public void setAssignedWorkers(Set<String> assignedWorkers) {
        this.assignedWorkers = assignedWorkers;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Set<NoteViewModel> getNotes() {
        return notes;
    }

    public void setNotes(Set<NoteViewModel> notes) {
        this.notes = notes;
    }

}
