package org.softuni.maintenancemanager.notes.service.interfaces;

import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;

import java.util.Set;

public interface NotesService {

    Set<NoteViewModel> getBySearchWord(String searchWord);

    Set<NoteViewModel> getByNotableId(String id);


    String deleteNote(String editor, Long id);

    NoteViewModel editNote(String editor, String noteText, Long id);
}
