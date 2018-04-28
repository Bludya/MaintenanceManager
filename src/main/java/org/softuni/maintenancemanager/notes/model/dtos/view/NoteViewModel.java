package org.softuni.maintenancemanager.notes.model.dtos.view;


public class NoteViewModel {

    private Long id;

    private String text;

    private String author;

    public NoteViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String note) {
        this.text = note;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
