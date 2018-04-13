package org.softuni.maintenancemanager.notes.controller;

import org.softuni.maintenancemanager.notes.model.dtos.binding.NoteBindModel;
import org.softuni.maintenancemanager.notes.service.interfaces.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/notes")
public class NoteController {
    private NotesService notesService;

    @Autowired
    public NoteController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/{id}")
    public Object getNotesById(@PathVariable String id) {
        return this.notesService.getByNotableId(id);
    }

    @GetMapping("/search/{searchWord}")
    public Object getNotesBySearchWord(@PathVariable String searchWord) {
        return this.notesService.getBySearchWord(searchWord);
    }

    @PostMapping("/add")
    public Object addNote(@RequestParam NoteBindModel noteBindModel,
                          Authentication authentication) {
        return this.notesService.addNote(authentication.getName(), noteBindModel);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteNote(@PathVariable Long id,
                             Authentication authentication) {
        return this.notesService.deleteNote(authentication.getName(), id);
    }

    @PutMapping("/edit/{id}")
    public Object editNote(@PathVariable Long id,
                           @RequestParam NoteBindModel noteBindModel,
                           Authentication authentication) {
        return this.notesService.editNote(authentication.getName(), noteBindModel, id);
    }
}
