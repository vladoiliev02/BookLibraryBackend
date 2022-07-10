package com.fmi.wdj.booklibrary.repository.book;

import com.fmi.wdj.booklibrary.model.book.BookGenre;
import com.fmi.wdj.booklibrary.model.book.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    BookGenre findByGenre(Genre genre);
}
