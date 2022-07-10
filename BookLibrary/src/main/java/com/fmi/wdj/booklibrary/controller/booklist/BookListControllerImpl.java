package com.fmi.wdj.booklibrary.controller.booklist;

import com.fmi.wdj.booklibrary.dto.booklist.BookListDto;
import com.fmi.wdj.booklibrary.dto.booklist.CreateUpdateBookListDto;
import com.fmi.wdj.booklibrary.mapper.booklist.BookListMapper;
import com.fmi.wdj.booklibrary.model.booklist.BookList;
import com.fmi.wdj.booklibrary.service.booklist.BookListService;
import com.fmi.wdj.booklibrary.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO:
 *      - Add notes for the books in the list, which the owner of the list can edit.
 *      - Allow any user to search for a book in the list by ISBN.
 *
 * */
@RestController
@RequestMapping("/api/book-lists")
public class BookListControllerImpl implements BookListController {

    private final BookListService bookListService;
    private final BookListMapper bookListMapper;

    @Autowired
    public BookListControllerImpl(BookListService bookListService, BookListMapper bookListMapper) {
        this.bookListService = bookListService;
        this.bookListMapper = bookListMapper;
    }

    @Override
    @GetMapping("/all")
    public List<BookListDto> getAllList() {
        return bookListService.getAllLists()
            .stream()
            .map(bookListMapper::toBookListDto)
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping("username/{username}")
    public List<BookListDto> getAllByUser(@PathVariable String username) {
        return bookListService.getByUser(username)
            .stream()
            .map(bookListMapper::toBookListDto)
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping("listName/{bookListName}")
    public List<BookListDto> getAllWithName(@PathVariable String bookListName) {
        return bookListService.getByName(bookListName)
            .stream()
            .map(bookListMapper::toBookListDto)
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping("/{username}/{bookListName}")
    public BookListDto getUserListByName(@PathVariable String username, @PathVariable String bookListName) {
        return bookListService.getByNameAndUser(bookListName, username)
            .map(bookListMapper::toBookListDto)
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "User %s, has no list with name %s", username, bookListName
            )));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookListDto createBookList(@RequestBody @Valid CreateUpdateBookListDto bookListDto, Principal principal) {
        BookList newList = bookListMapper.fromCreateUpdateDto(bookListDto, principal.getName());

        newList = bookListService.createList(newList);
        return bookListMapper.toBookListDto(newList);
    }

    @Override
    @PutMapping
    public ResponseEntity<BookListDto> updateBookList(@RequestBody @Valid CreateUpdateBookListDto bookListDto,
                                                      Principal principal) {
        BookList list = bookListMapper.fromCreateUpdateDto(bookListDto, principal.getName());

        BookList existing = bookListService.getByNameAndUser(list.getName(), list.getOwner().getUsername())
            .orElse(null);

        BookListDto result;
        HttpStatus resultStatus;
        if (existing != null) {
            list.setBookListId(existing.getBookListId());
            BookList updated = bookListService.updateList(list);
            result = bookListMapper.toBookListDto(updated);
            resultStatus = HttpStatus.OK;
        } else {
            BookList newList = bookListService.createList(list);
            result = bookListMapper.toBookListDto(newList);
            resultStatus = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(result, resultStatus);
    }

    @Override
    @PatchMapping("/add")
    public BookListDto addBook(@RequestParam String bookListName, @RequestParam String isbn, Principal principal) {
        BookList list = bookListService.getByNameAndUser(bookListName, principal.getName())
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "You do not own a list with the name: %s.", bookListName
            )));

        bookListService.addBook(list, isbn);

        return bookListMapper.toBookListDto(list);
    }

    @Override
    @PatchMapping("/remove")
    public BookListDto removeBook(@RequestParam String bookListName, @RequestParam String isbn, Principal principal) {
        BookList list = bookListService.getByNameAndUser(bookListName, principal.getName())
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "You do not own a list with the name: %s.", bookListName
            )));

        bookListService.removeBook(list, isbn);

        return bookListMapper.toBookListDto(list);
    }

    @Override
    @DeleteMapping("/{listName}")
    public void removeBookList(@PathVariable String listName, Principal principal) {
        BookList list = bookListService.getByNameAndUser(listName, principal.getName())
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "You do not own a list with the name: %s.", listName
            )));

        bookListService.deleteList(list.getBookListId());
    }
}
