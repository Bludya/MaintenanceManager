package org.softuni.maintenancemanager.notes.model.dtos.view;


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
