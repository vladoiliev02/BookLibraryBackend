package com.fmi.wdj.booklibrary.repository.booklist;

import com.fmi.wdj.booklibrary.model.booklist.BookList;
import com.fmi.wdj.booklibrary.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookListRepository extends JpaRepository<BookList, Long> {
    List<BookList> findByName(String name);

    List<BookList> findByOwner(User owner);

    Optional<BookList> findByNameAndOwner(String name, User owner);
}
