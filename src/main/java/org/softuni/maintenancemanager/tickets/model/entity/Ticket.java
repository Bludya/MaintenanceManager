package org.softuni.maintenancemanager.tickets.model.entity;

import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.softuni.maintenancemanager.tasks.model.entity.Task;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ticket_text")
    private String ticket_text;

    @Column(name = "date_opened")
    private Date dateOpened;

    @Column(name = "date_closed")
    private Date dateClosed;

    @Column(name = "closed")
    private Boolean closed;

    @Column(name = "reported_by")
    private String reportedBy;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "ticket")
    private Set<Task> tasks;

    @OneToMany
    private Set<Note> notes;

    @ManyToOne
    private User author;

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public String getTicket_text() {
        return ticket_text;
    }

    public void setTicket_text(String ticket_text) {
        this.ticket_text = ticket_text;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(this.tasks);
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
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
