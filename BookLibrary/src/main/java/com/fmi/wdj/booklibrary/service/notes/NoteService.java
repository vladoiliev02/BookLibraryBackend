package com.fmi.wdj.booklibrary.service.notes;

import com.fmi.wdj.booklibrary.model.notes.Note;
import com.fmi.wdj.booklibrary.model.notes.NoteData;
import com.fmi.wdj.booklibrary.model.user.User;

import java.util.List;

public interface NoteService {

    NoteData getNoteDataForUser(User owner);

    List<Note> getNotesForBook(String isbn, User owner);

    Note addNote(String isbn, String note, User owner);

    void removeNote(long id, User owner);
}
