package com.fmi.wdj.booklibrary.dto.booklist;

import com.fmi.wdj.booklibrary.dto.book.BookDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BookListDto {

    @NotNull(message = "The name of the list cannot be null.")
    @NotEmpty(message = "The name of the list cannot be empty.")
    @NotBlank(message = "The name of the list cannot be blank.")
    private String listName;

    @NotNull(message = "The name of the owner cannot be null.")
    @NotEmpty(message = "The name of the owner cannot be empty.")
    @NotBlank(message = "The name of the owner cannot be blank.")
    private String ownerUsername;

    @NotNull(message = "The list of books cannot be null.")
    private List<BookDto> books;
}
