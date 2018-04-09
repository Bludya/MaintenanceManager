package org.softuni.maintenancemanager.notes.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFound;
import org.softuni.maintenancemanager.logger.service.interfaces.LogService;
import org.softuni.maintenancemanager.notes.model.dtos.binding.NoteBindModel;
import org.softuni.maintenancemanager.notes.model.dtos.view.NoteViewModel;
import org.softuni.maintenancemanager.notes.model.entity.Note;
import org.softuni.maintenancemanager.notes.model.repositories.NotesRepository;
import org.softuni.maintenancemanager.notes.service.interfaces.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class NotesServiceImpl implements NotesService {

    private NotesRepository notesRepository;
    private LogService logService;
    private ModelMapper modelMapper;

    @Autowired
    public NotesServiceImpl(
            NotesRepository notesRepository,
            LogService logService,
            ModelMapper modelMapper) {
        this.notesRepository = notesRepository;
        this.logService = logService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<NoteViewModel> getBySearchWord(String searchWord) {
        return this.notesRepository.findAllByTextContains(searchWord);
    }

    @Override
    public Set<NoteViewModel> getByNotableId(String id) {
        return this.notesRepository.findAllByAuthor_Id(id);
    }

    @Override
    public NoteViewModel addNote(String editor, NoteBindModel noteBindModel) {
        Note note = new Note();
        note.setText(noteBindModel.text);
        this.notesRepository.save(note);

        this.logService.addLog(editor, "Added note with id: " + note.getId());
        return this.modelMapper.map(note, NoteViewModel.class);
    }

    @Override
    public String deleteNote(String editor, Long id) {
        if (this.notesRepository.existsById(id)) {
            throw new EntryNotFound();
        }

        this.notesRepository.deleteById(id);

        this.logService.addLog(editor, "Deleted note with id: " + id);
        return "Deletion successful.";
    }

    @Override
    public NoteViewModel editNote(String editor, NoteBindModel noteBindModel, Long id) {
        Optional<Note> noteOptional = this.notesRepository.findById(id);

        if (!noteOptional.isPresent()) {
            throw new EntryNotFound();
        }

        Note note = noteOptional.get();
        note.setText(noteBindModel.text);

        this.notesRepository.save(note);

        this.logService.addLog(editor, "Added note with id: " + note.getId());
        return this.modelMapper.map(note, NoteViewModel.class);
    }
}
