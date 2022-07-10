package com.fmi.wdj.booklibrary.controller.book;

import com.fmi.wdj.booklibrary.dto.book.BookDto;
import com.fmi.wdj.booklibrary.mapper.book.BookMapper;
import com.fmi.wdj.booklibrary.model.book.Book;
import com.fmi.wdj.booklibrary.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookControllerImpl implements BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookControllerImpl(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Override
    @GetMapping("/all")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks()
            .stream()
            .map(bookMapper::toBookDto)
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping
    public List<BookDto> getBookByCriteria(@RequestParam(value = "title", required = false) String title,
                                           @RequestParam(value = "author", required = false) String author,
                                           @RequestParam(value = "genre", required = false) String genre) {
        return bookService.getBooksByCriteria(title, author,
                Optional.ofNullable(genre)
                    .map(s -> s.split(","))
                    .orElse(null))
            .stream()
            .map(bookMapper::toBookDto)
            .collect(Collectors.toList());
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody @Valid BookDto inputBook) {
        Book newBook = bookMapper.fromBookDto(inputBook);
        bookService.saveBook(newBook);
        return bookMapper.toBookDto(newBook);
    }

    @Override
    @PutMapping
    public ResponseEntity<BookDto> updateBook(@RequestBody @Valid BookDto inputBook) {
        boolean alreadyExists = bookService.exists(inputBook.getISBN());

        Book newBook = bookMapper.fromBookDto(inputBook);
        bookService.saveBook(newBook);

        BookDto result = bookMapper.toBookDto(newBook);
        return alreadyExists ? new ResponseEntity<>(result, HttpStatus.OK) :
            new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
        bookService.removeBook(isbn);
    }
}