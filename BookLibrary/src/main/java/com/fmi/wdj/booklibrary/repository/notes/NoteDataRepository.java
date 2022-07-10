package com.fmi.wdj.booklibrary.repository.notes;

import com.fmi.wdj.booklibrary.model.notes.NoteData;
import com.fmi.wdj.booklibrary.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteDataRepository extends JpaRepository<NoteData, Long> {
    Optional<NoteData> findByOwner(User owner);
}
