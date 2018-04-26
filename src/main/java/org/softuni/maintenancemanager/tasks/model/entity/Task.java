package org.softuni.maintenancemanager.tasks.model.entity;

import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.softuni.maintenancemanager.tickets.model.entity.Ticket;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "info")
    private String info;

    @Column(name = "date_created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

    @Column(name = "deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "completion_note")
    private String completionNote;


    @ManyToOne
    private User userCompleted;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "tasks_users",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> assignedWorkers;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Ticket ticket;

    @OneToMany
    private Set<Note> notes;

    @ManyToOne
    private User author;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Set<User> getAssignedWorkers() {
        return Collections.unmodifiableSet(this.assignedWorkers);
    }

    public void setAssignedWorkers(Set<User> assignedWorkers) {
        this.assignedWorkers = assignedWorkers;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Set<Note> getNotes() {
        return Collections.unmodifiableSet(this.notes);
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public User getUserCompleted() {
        return userCompleted;
    }

    public void setUserCompleted(User userCompleted) {
        this.userCompleted = userCompleted;
    }

    public void addNote(Note note){
        this.notes.add(note);
    }
}
