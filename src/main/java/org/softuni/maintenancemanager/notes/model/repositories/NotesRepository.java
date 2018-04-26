package org.softuni.maintenancemanager.notes.model.repositories;

import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:8080")
public interface NotesRepository extends JpaRepository<Note, Long> {

    Set<NoteViewModel> findAllByTextContains(String searchWord);

    Set<NoteViewModel> findAllByAuthor_Id(String id);
}
