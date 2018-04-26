package org.softuni.maintenancemanager.notes.model.dtos.view;

import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Note.class, name = "noteViewModel")
public class NoteViewModel {

    private Long id;

    private String note;

    private String author;

    public NoteViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
