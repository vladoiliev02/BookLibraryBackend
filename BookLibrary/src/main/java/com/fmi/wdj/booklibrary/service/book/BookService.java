package com.fmi.wdj.booklibrary.service.book;

import com.fmi.wdj.booklibrary.model.book.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book saveBook(Book newBook);

    void removeBook(String ISBN);

    List<Book> getAllBooks();

    Optional<Book> getBookByISBN(String isbn);

    boolean exists(String isbn);

    List<Book> getBooksByCriteria(String title, String author, String[] genres);
}
