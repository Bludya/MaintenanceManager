package org.softuni.maintenancemanager.tasks.model.entity;

import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.softuni.maintenancemanager.tickets.model.entity.Ticket;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
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
    private Date dateCreated;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "completed")
    private Boolean completed;

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
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
}
