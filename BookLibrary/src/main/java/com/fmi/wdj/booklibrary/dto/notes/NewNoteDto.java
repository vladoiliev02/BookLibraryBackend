package com.fmi.wdj.booklibrary.dto.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewNoteDto {

    @NotNull(message = "ISBN cannot be null.")
    @NotBlank(message = "ISBN cannot be blank.")
    @NotEmpty(message = "ISBN cannot be empty.")
    private String ISBN;

    @NotNull(message = "Content cannot be null.")
    @NotBlank(message = "Content cannot be blank.")
    @NotEmpty(message = "Content cannot be empty.")
    private String content;
}
