package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.Note;
import dev.fespinosa.cloudstorage.model.User;
import dev.fespinosa.cloudstorage.services.NoteService;
import dev.fespinosa.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public ResponseEntity<Note> addNote(Principal principal, @RequestBody Note note) {
        Optional<User> userOpt = userService.findUserByUsername(principal.getName());
        userOpt.ifPresent(u -> note.setUserId(u.getUserId()));
        int notesCreated = noteService.createNote(note);
        if (notesCreated <= 0) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Note> deleteNote(@RequestBody Note note) {
        noteService.deleteNote(note);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }


    @GetMapping("/list")
    public String getNoteList(Principal principal, Model model) {
        List<Note> notesByUsername = noteService.getAllNotesByUsername(principal.getName());
        model.addAttribute("notes", notesByUsername);
        return "home::note_list";
    }
}
