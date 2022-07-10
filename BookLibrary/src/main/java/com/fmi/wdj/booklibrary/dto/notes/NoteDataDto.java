package com.fmi.wdj.booklibrary.dto.notes;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
public class NoteDataDto {

    @NotNull(message = "Notes cannot be null.")
    // Maps book isbn to notes for the book
    private Map<String, List<ResultNoteDto>> notes;
}
