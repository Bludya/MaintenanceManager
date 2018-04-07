package org.softuni.maintenancemanager.projects.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.maintenancemanager.auth.model.entity.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "information")
    private String information;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    private User manager;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "projects_systems",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "system_id")}
    )
    private Set<System> systems;

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "project")
    private Set<Note> notes;

    @OneToMany(mappedBy = "project")
    private Set<Ticket> tickets;

    public Project() {
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

    public Set<System> getSystems() {
        return Collections.unmodifiableSet(this.systems);
    }

    public void setSystems(Set<System> systems) {
        this.systems = systems;
    }

    public Set<Note> getNotes() {
        return Collections.unmodifiableSet(this.notes);
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
}
