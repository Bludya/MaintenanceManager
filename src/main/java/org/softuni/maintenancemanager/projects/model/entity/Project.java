package org.softuni.maintenancemanager.projects.model.entity;

import org.hibernate.annotations.Type;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.softuni.maintenancemanager.tasks.model.entity.Task;
import org.softuni.maintenancemanager.tickets.model.entity.Ticket;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "project_name", unique = true, nullable = false)
    private String projectName;

    @Column(name = "information")
    private String information;

    @Column(name = "date_created")
    @Type(type="date")
    private Date dateCreated;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    private User manager;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "projects_systems",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "system_id")}
    )
    private Set<ProjectSystem> systems;

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "project")
    private Set<Ticket> tickets;

    @OneToMany
    private Set<Note> notes;

    @ManyToOne
    private User author;

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<Note> getNotes() {
        return Collections.unmodifiableSet(this.notes);
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(this.tasks);
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Ticket> getTickets() {
        return Collections.unmodifiableSet(this.tickets);
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<ProjectSystem> getSystems() {
        return Collections.unmodifiableSet(this.systems);
    }

    public void setSystems(Set<ProjectSystem> systems) {
        this.systems = systems;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
