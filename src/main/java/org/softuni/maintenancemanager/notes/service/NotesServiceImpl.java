package org.softuni.maintenancemanager.notes.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.appUtils.CharacterEscapes;
import org.softuni.maintenancemanager.errorHandling.exceptions.CantBeEmptyException;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFoundException;
import org.softuni.maintenancemanager.logger.service.interfaces.Logger;
import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.softuni.maintenancemanager.notes.model.repositories.NotesRepository;
import org.softuni.maintenancemanager.notes.service.interfaces.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class NotesServiceImpl implements NotesService {

    private NotesRepository notesRepository;
    private Logger logger;
    private ModelMapper modelMapper;

    @Autowired
    public NotesServiceImpl(
            NotesRepository notesRepository,
            Logger logger,
            ModelMapper modelMapper) {
        this.notesRepository = notesRepository;
        this.logger = logger;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<NoteViewModel> getBySearchWord(String searchWord) {
        searchWord = CharacterEscapes.escapeString(searchWord);

        return this.notesRepository.findAllByTextContains(searchWord);
    }

    @Override
    public Set<NoteViewModel> getByNotableId(String id) {
        id = CharacterEscapes.escapeString(id);
        return this.notesRepository.findAllByAuthor_Id(id);
    }

    @Override
    public String deleteNote(String editor, Long id) {
        editor = CharacterEscapes.escapeString(editor);

        if (this.notesRepository.existsById(id)) {
            throw new EntryNotFoundException();
        }

        this.notesRepository.deleteById(id);

        this.logger.addLog(editor, "Deleted note with id: " + id);
        return "Deletion successful.";
    }

    @Override
    public NoteViewModel editNote(String editor, String noteText, Long id) {
        noteText = CharacterEscapes.escapeString(noteText);
        editor = CharacterEscapes.escapeString(editor);

        Optional<Note> noteOptional = this.notesRepository.findById(id);

        if (!noteOptional.isPresent()) {
            throw new EntryNotFoundException();
        }

        Note note = noteOptional.get();
        note.setText(noteText);

        this.notesRepository.save(note);

        this.logger.addLog(editor, "Edited note with id: " + note.getId());
        return this.modelMapper.map(note, NoteViewModel.class);
    }

    @Override
    public Note createNote(String author, String noteText){

        if(noteText == null || noteText.equals("")){
            throw new CantBeEmptyException("Note");
        }

        noteText = CharacterEscapes.escapeString(noteText);

        Note note = new Note();
        note.setText(noteText);
        note.setDateWritten(LocalDate.now());

        this.notesRepository.save(note);
        this.logger.addLog(author, "Added new note.");
        return note;
    }
}
