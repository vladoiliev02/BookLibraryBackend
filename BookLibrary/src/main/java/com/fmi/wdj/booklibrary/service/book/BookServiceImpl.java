package com.fmi.wdj.booklibrary.service.book;

import com.fmi.wdj.booklibrary.model.book.Book;
import com.fmi.wdj.booklibrary.model.book.Genre;
import com.fmi.wdj.booklibrary.repository.book.BookGenreRepository;
import com.fmi.wdj.booklibrary.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookGenreRepository bookGenreRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    @Transactional
    public Book saveBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Override
    @Transactional
    public void removeBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Consider throwing an exception instead of returning null
    @Override
    public Optional<Book> getBookByISBN(String isbn) {
        return bookRepository.findById(isbn);
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findAll()
            .stream()
            .filter((Book book) -> book.getTitle().equalsIgnoreCase(title))
            .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public List<Book> getBooksByCriteria(String title, String author, String[] genres) {
        Stream<Book> result = bookRepository.findAll().stream();

        if (title != null) {
            result = result.filter(book -> book.getTitle().equalsIgnoreCase(title));
        }

        if (author != null) {
            result = result.filter(book -> book.getAuthor().equalsIgnoreCase(author));
        }

        if (genres != null && genres.length > 0) {
            for (String genre : genres) {
                result = result.filter(
                    book -> book.getGenre().contains(
                        bookGenreRepository.findByGenre(Genre.valueOf(genre.toUpperCase()))));
            }
        }

        return result.collect(Collectors.toList());
    }
}
