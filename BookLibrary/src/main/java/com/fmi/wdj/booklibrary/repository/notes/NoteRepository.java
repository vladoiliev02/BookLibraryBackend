package com.fmi.wdj.booklibrary.repository.notes;

import com.fmi.wdj.booklibrary.model.notes.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
