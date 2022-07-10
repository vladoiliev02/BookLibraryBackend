package com.fmi.wdj.booklibrary.dto.booklist;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateUpdateBookListDto {

    @NotNull(message = "The name of the list cannot be null.")
    @NotBlank(message = "The name of the list cannot be blank.")
    @NotEmpty(message = "The name of the list cannot be empty.")
    private String listName;

    private List<String> bookISBNs;
}
