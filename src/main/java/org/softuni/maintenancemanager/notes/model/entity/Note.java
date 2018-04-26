package org.softuni.maintenancemanager.notes.model.entity;

import org.softuni.maintenancemanager.auth.model.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date_written")

    private LocalDate dateWritten;

    @ManyToOne
    private User author;

    public Note() {
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDateWritten() {
        return dateWritten;
    }

    public void setDateWritten(LocalDate dateWritten) {
        this.dateWritten = dateWritten;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
