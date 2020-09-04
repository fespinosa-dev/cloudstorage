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
@RequestMapping("/note")
public class NoteController {

    private final String NOTE_ADDED_MSG = "\"Note was successfully added!\"";
    private final String NOTE_DELETED_MSG = "\"Note was successfully deleted!\"";
    private final String NOTE_UPDATED_MSG = "\"Note was successfully updated!\"";
    private final String NOTE_ERROR_MSG = "\"There was an error adding the note!\"";

    private NoteService noteService;
    private UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @PostMapping(value = "/add", produces = "text/plain")
    public ResponseEntity<String> addNote(Principal principal, @RequestBody Note note) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(NOTE_ADDED_MSG, HttpStatus.OK);
        Optional<User> userOpt = userService.findUserByUsername(principal.getName());
        userOpt.ifPresent(u -> note.setUserId(u.getUserId()));
        int notesCreated = noteService.createNote(note);
        if (notesCreated <= 0) {
            responseEntity = new ResponseEntity<>(NOTE_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteNote(@RequestBody Note note) {
        noteService.deleteNote(note);
        return new ResponseEntity<>(NOTE_DELETED_MSG, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateNote(@RequestBody Note note) {
        noteService.updateNote(note);
        return new ResponseEntity<>(NOTE_UPDATED_MSG, HttpStatus.OK);
    }


    @GetMapping("/list")
    public String getNoteList(Principal principal, Model model) {
        List<Note> notesByUsername = noteService.getAllNotesByUsername(principal.getName());
        model.addAttribute("notes", notesByUsername);
        return "home::note_list";
    }

}
