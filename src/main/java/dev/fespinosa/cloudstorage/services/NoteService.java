package dev.fespinosa.cloudstorage.services;

import dev.fespinosa.cloudstorage.mappers.NoteMapper;
import dev.fespinosa.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    @Autowired
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    public int createNote(Note note) {
        return noteMapper.insert(note);
    }

    public void deleteNote(Note note) {
        noteMapper.delete(note);
    }

    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    public List<Note> getAllNotesByUsername(String username) {
        return noteMapper.getNotesByUsername(username);
    }
}
