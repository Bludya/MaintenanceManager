package org.softuni.maintenancemanager.notes.model.dtos.view;

import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = Note.class, name = "noteViewModel")
public interface NoteViewModel {

    String getText();

    Date getDateWritten();

    @Value("#{target.getUser().getUsername()}")
    String creator();
}
