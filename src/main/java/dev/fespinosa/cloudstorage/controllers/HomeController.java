package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.Credentials;
import dev.fespinosa.cloudstorage.model.Note;
import dev.fespinosa.cloudstorage.services.CredentialService;
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
    private CredentialService credentialService;

    @Autowired
    public HomeController(NoteService noteService, CredentialService credentialService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String getNotes(Principal principal, Model model) {
        String userName = principal.getName();
        List<Note> notes = noteService.getAllNotesByUsername(userName);
        List<Credentials> credentials = credentialService.getAllCredentialsByUsername(userName);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        return "home";
    }
}
