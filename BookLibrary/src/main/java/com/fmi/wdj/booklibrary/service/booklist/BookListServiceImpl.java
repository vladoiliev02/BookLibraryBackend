package com.fmi.wdj.booklibrary.service.booklist;

import com.fmi.wdj.booklibrary.model.book.Book;
import com.fmi.wdj.booklibrary.model.booklist.BookList;
import com.fmi.wdj.booklibrary.model.user.User;
import com.fmi.wdj.booklibrary.repository.booklist.BookListRepository;
import com.fmi.wdj.booklibrary.service.book.BookService;
import com.fmi.wdj.booklibrary.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookListServiceImpl implements BookListService {
    private final BookService bookService;
    private final UserService userService;
    private final BookListRepository bookListRepository;

    @Autowired
    public BookListServiceImpl(BookService bookService, UserService userService,
                               BookListRepository bookListRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.bookListRepository = bookListRepository;
    }


    @Override
    public BookList createList(BookList bookList) {
        // TODO: Add already exists if user has a book list with this name;
        return bookListRepository.save(bookList);
    }

    @Override
    public BookList updateList(BookList bookList) {
        // TODO: Add validation
        return bookListRepository.save(bookList);
    }

    @Override
    public List<BookList> getAllLists() {
        return bookListRepository.findAll();
    }

    @Override
    public List<BookList> getByName(String name) {
        return bookListRepository.findByName(name);
    }

    @Override
    public List<BookList> getByUser(String username) {
        User owner = userService.getUserByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(
                String.format("User %s, not found.", username)));
        return bookListRepository.findByOwner(owner);
    }

    @Override
    public Optional<BookList> getByNameAndUser(String listName, String username) {
        User owner = userService.getUserByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(
                String.format("User %s, not found.", username)));
        return bookListRepository.findByNameAndOwner(listName, owner);
    }

    @Override
    public BookList addBook(BookList bookList, String isbn) {
        // TODO: Check if book is already added
        Book book = bookService.getBookByISBN(isbn)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Book with ISBN: %s, not found.", isbn)));

        bookList.getBooks().add(book);
        bookListRepository.save(bookList);
        return bookList;
    }

    @Override
    public BookList removeBook(BookList bookList, String isbn) {
        // TODO: Check if book is in the list
        Book book = bookService.getBookByISBN(isbn)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Book with ISBN: %s, not found.", isbn)));

        bookList.getBooks().remove(book);
        bookListRepository.save(bookList);
        return bookList;
    }

    @Override
    public void deleteList(Long id) {
        bookListRepository.deleteById(id);
    }
}
