package org.softuni.maintenancemanager.notes.model.repositories;

import org.softuni.maintenancemanager.logger.model.dtos.view.LogViewModel;
import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "notes", path = "notes", excerptProjection = LogViewModel.class)
@CrossOrigin(origins = "http://localhost:8080")
public interface NotesRepository extends JpaRepository<Note, Long> {

    Set<NoteViewModel> findAllByTextContains(String searchWord);

    Set<NoteViewModel> findAllByAuthor_Id(String id);
}
