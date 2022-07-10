package com.fmi.wdj.booklibrary.mapper.booklist;

import com.fmi.wdj.booklibrary.dto.booklist.BookListDto;
import com.fmi.wdj.booklibrary.dto.booklist.CreateUpdateBookListDto;
import com.fmi.wdj.booklibrary.mapper.book.BookMapper;
import com.fmi.wdj.booklibrary.model.book.Book;
import com.fmi.wdj.booklibrary.model.booklist.BookList;
import com.fmi.wdj.booklibrary.model.user.User;
import com.fmi.wdj.booklibrary.service.book.BookService;
import com.fmi.wdj.booklibrary.service.booklist.BookListService;
import com.fmi.wdj.booklibrary.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookListMapper {

    private final BookListService bookListService;
    private final UserService userService;
    private final BookMapper bookMapper;

    private final BookService bookService;

    @Autowired
    public BookListMapper(BookListService bookListService, UserService userService, BookMapper bookMapper,
                          BookService bookService) {
        this.bookListService = bookListService;
        this.userService = userService;
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    public BookListDto toBookListDto(BookList bookList) {
        BookListDto dto = new BookListDto();

        dto.setListName(bookList.getName());
        dto.setOwnerUsername(bookList.getOwner().getUsername());
        dto.setBooks(bookList.getBooks()
            .stream()
            .map(bookMapper::toBookDto)
            .collect(Collectors.toList()));

        return dto;
    }

    public BookList fromBookListDto(BookListDto dto) {
        BookList bookList = new BookList();

        bookList.setName(dto.getListName());
        bookList.setBooks(dto.getBooks()
            .stream()
            .map(bookMapper::fromBookDto)
            .collect(Collectors.toList()));

        User owner = userService.getUserByUsername(dto.getOwnerUsername())
            .orElseThrow(() -> new IllegalArgumentException(
                String.format("User %s, not found.", dto.getOwnerUsername())));
        bookList.setOwner(owner);

        Long oldListId = bookListService.getByNameAndUser(dto.getListName(), dto.getOwnerUsername())
            .map(BookList::getBookListId)
            .orElse(null);

        bookList.setBookListId(oldListId);

        return bookList;
    }

    public BookList fromCreateUpdateDto(CreateUpdateBookListDto dto, String ownerName) {
        BookList result = new BookList();

        result.setName(dto.getListName());

        List<Book> books = dto.getBookISBNs()
            .stream()
            .map(isbn -> bookService.getBookByISBN(isbn)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                    "Book with isbn: %s, does not exist.", isbn
                ))))
            .collect(Collectors.toList());
        result.setBooks(books);

        if (ownerName != null) {
            result.setOwner(userService.getUserByUsername(ownerName)
                .orElseThrow(() -> new IllegalStateException(String.format(
                    "User: %s was not found.", ownerName))));
        }

        return result;
    }

    public BookList fromCreateUpdateDto(CreateUpdateBookListDto dto) {
        return fromCreateUpdateDto(dto, null);
    }
}
