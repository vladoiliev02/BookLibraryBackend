package com.fmi.wdj.booklibrary.repository.book;

import com.fmi.wdj.booklibrary.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

}
