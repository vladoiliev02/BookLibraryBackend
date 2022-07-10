package com.fmi.wdj.booklibrary.model.booklist;

import com.fmi.wdj.booklibrary.model.book.Book;
import com.fmi.wdj.booklibrary.model.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
@Table(
    name = "book_lists",
    indexes = {@Index(columnList = "name"),
               @Index(columnList = "owner")},
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner"})}
)
@Data
public class BookList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookListId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
        name = "books_lists",
        joinColumns = @JoinColumn(name = "book_list_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
}
