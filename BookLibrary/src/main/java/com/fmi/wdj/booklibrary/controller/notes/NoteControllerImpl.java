package com.fmi.wdj.booklibrary.controller.notes;

import com.fmi.wdj.booklibrary.dto.notes.NewNoteDto;
import com.fmi.wdj.booklibrary.dto.notes.NoteDataDto;
import com.fmi.wdj.booklibrary.dto.notes.ResultNoteDto;
import com.fmi.wdj.booklibrary.mapper.notes.NoteMapper;
import com.fmi.wdj.booklibrary.model.notes.Note;
import com.fmi.wdj.booklibrary.model.notes.NoteData;
import com.fmi.wdj.booklibrary.model.user.User;
import com.fmi.wdj.booklibrary.service.notes.NoteService;
import com.fmi.wdj.booklibrary.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteControllerImpl implements NoteController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;
    private final UserService userService;

    @Autowired
    public NoteControllerImpl(NoteService noteService, NoteMapper noteMapper, UserService userService) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    @Override
    @GetMapping("/all")
    public NoteDataDto getAllNotes(Principal principal) {
        User owner = userService.getUserByUsername(principal.getName())
            .orElseThrow(() -> new IllegalStateException(String.format("User: %s, not found", principal.getName())));
        NoteData noteData = noteService.getNoteDataForUser(owner);
        return noteMapper.toNoteDto(noteData);
    }

    @Override
    @GetMapping("/{isbn}")
    public List<ResultNoteDto> getNotesForBook(@PathVariable String isbn, Principal principal) {
        User owner = userService.getUserByUsername(principal.getName())
            .orElseThrow(() -> new IllegalStateException(String.format("User: %s, not found", principal.getName())));
        List<Note> notes = noteService.getNotesForBook(isbn, owner);

        return notes.stream()
            .map(noteMapper::toResultNoteDto)
            .collect(Collectors.toList());
    }

    @Override
    @PatchMapping
    public ResultNoteDto addNote(@RequestBody @Valid NewNoteDto newNoteDto, Principal principal) {
        User owner = userService.getUserByUsername(principal.getName())
            .orElseThrow(() -> new IllegalStateException(String.format("User: %s, not found", principal.getName())));

        Note note = noteService.addNote(newNoteDto.getISBN(), newNoteDto.getContent(), owner);

        return noteMapper.toResultNoteDto(note);
    }

    @Override
    @DeleteMapping("/{id}")
    public void removeNote(@PathVariable long id, Principal principal) {
        User owner = userService.getUserByUsername(principal.getName())
            .orElseThrow(() -> new IllegalStateException(String.format("User: %s, not found", principal.getName())));

        noteService.removeNote(id, owner);
    }
}
