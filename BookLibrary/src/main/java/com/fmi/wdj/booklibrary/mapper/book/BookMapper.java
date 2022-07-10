package com.fmi.wdj.booklibrary.mapper.book;

import com.fmi.wdj.booklibrary.dto.book.BookDto;
import com.fmi.wdj.booklibrary.model.book.Book;
import com.fmi.wdj.booklibrary.model.book.BookGenre;
import com.fmi.wdj.booklibrary.model.book.Genre;
import com.fmi.wdj.booklibrary.repository.book.BookGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    @Autowired
    private BookGenreRepository bookGenreRepository;

    public Book fromBookDto(BookDto iBook) {
        Book book = new Book();

        Set<BookGenre> genres = transformGenresToBookGenres(iBook.getGenre());

        book.setAuthor(iBook.getAuthor());
        book.setTitle(iBook.getTitle());
        book.setGenre(genres);
        book.setISBN(iBook.getISBN());

        return book;
    }

    public BookDto toBookDto(Book book) {
        BookDto oBook = new BookDto();

        oBook.setAuthor(book.getAuthor());
        oBook.setISBN(book.getISBN());
        oBook.setTitle(book.getTitle());

        Set<Genre> resultSet = book.getGenre()
            .stream()
            .map(BookGenre::getGenre)
            .collect(Collectors.toSet());

        oBook.setGenre(resultSet);

        return oBook;
    }

    private Set<BookGenre> transformGenresToBookGenres(Set<Genre> genres) {
        return genres
            .stream()
            .map(genre -> {
                BookGenre bookGenre = bookGenreRepository.findByGenre(genre);
                if (bookGenre == null) {
                    throw new IllegalArgumentException(String.format("Genre: %s, not found.", genre.name()));
                }

                return bookGenre;
            })
            .collect(Collectors.toSet());

    }
}
