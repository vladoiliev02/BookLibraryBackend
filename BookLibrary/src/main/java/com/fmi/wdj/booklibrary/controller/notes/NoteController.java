package com.fmi.wdj.booklibrary.controller.notes;

import com.fmi.wdj.booklibrary.dto.notes.NewNoteDto;
import com.fmi.wdj.booklibrary.dto.notes.NoteDataDto;
import com.fmi.wdj.booklibrary.dto.notes.ResultNoteDto;

import java.security.Principal;
import java.util.List;

public interface NoteController {

    NoteDataDto getAllNotes(Principal principal);

    List<ResultNoteDto> getNotesForBook(String isbn, Principal principal);

    ResultNoteDto addNote(NewNoteDto newNoteDto, Principal principal);

    void removeNote(long id, Principal principal);
}