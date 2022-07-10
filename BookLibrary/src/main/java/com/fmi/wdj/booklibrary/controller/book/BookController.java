package com.fmi.wdj.booklibrary.controller.book;

import com.fmi.wdj.booklibrary.dto.book.BookDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookController {

    List<BookDto> getAllBooks();

    List<BookDto> getBookByCriteria(String title, String author, String genre);

    BookDto addBook(BookDto newBook);

    ResponseEntity<BookDto> updateBook(BookDto newBook);

    void deleteBook(String ISBN);
}
