package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.Note;
import dev.fespinosa.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private NoteService noteService;

    @Autowired
    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String getNotes(Principal principal, Model model) {
        List<Note> notesByUsername = noteService.getAllNotesByUsername(principal.getName());
        model.addAttribute("notes", notesByUsername);
        return "home";
    }
}
