package org.softuni.maintenancemanager.tasks.model.dtos.view;

import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;
import org.softuni.maintenancemanager.tasks.model.entity.Task;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.Set;

@Projection(types = Task.class, name = "taskViewModel")
public class TaskViewModel {
    private Long id;

    private String info;

    private LocalDate dateCreated;

    private LocalDate deadline;

    private Boolean completed;

    private LocalDate completionDate;

    private String completionNote;

    private String userCompleted;

    private Set<String> assignedWorkers;

    private String projectName;

    private Long ticketId;

    private String author;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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

    public String getCompletionNote() {
        return completionNote;
    }

    public void setCompletionNote(String completionNote) {
        this.completionNote = completionNote;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getUserCompleted() {
        return userCompleted;
    }

    public void setUserCompleted(String userCompleted) {
        this.userCompleted = userCompleted;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<NoteViewModel> getNotes() {
        return notes;
    }

    public void setNotes(Set<NoteViewModel> notes) {
        this.notes = notes;
    }
}
