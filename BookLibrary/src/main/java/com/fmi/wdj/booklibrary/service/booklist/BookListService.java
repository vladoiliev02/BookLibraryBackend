package com.fmi.wdj.booklibrary.service.booklist;

import com.fmi.wdj.booklibrary.model.booklist.BookList;

import java.util.List;
import java.util.Optional;

public interface BookListService {

    BookList createList(BookList bookList);

    BookList updateList(BookList bookList);

    List<BookList> getAllLists();

    List<BookList> getByName(String name);

    List<BookList> getByUser(String username);

    Optional<BookList> getByNameAndUser(String listName, String username);

    BookList addBook(BookList bookList, String isbn);

    BookList removeBook(BookList bookList, String isbn);

    void deleteList(Long id);
}
