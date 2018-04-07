package org.softuni.maintenancemanager.projects.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

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

    public Ticket() {
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
}
