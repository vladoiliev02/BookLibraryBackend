package com.fmi.wdj.booklibrary.dto.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultNoteDto {
    @NotNull(message = "Id cannot be null.")
    private Long id;

    @NotNull(message = "Id cannot be null.")
    @NotEmpty(message = "Id cannot be empty.")
    @NotBlank(message = "Id cannot be blank.")
    private String contents;
}
