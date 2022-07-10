package com.fmi.wdj.booklibrary.model.book;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "isbn", unique = true)
    @Length(min = 10, max = 10, message = "ISBN length must be 10")
    private String ISBN;

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be blank")
    @NotEmpty(message = "Title cannot be empty")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "Author cannot be null")
    @NotBlank(message = "Author cannot be blank")
    @NotEmpty(message = "Author cannot be empty")
    @Column(name = "author", nullable = false)
    private String author;

    @NotNull
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(forColumn = "genre", read = "UPPER(genre)")
    @ManyToMany
    @JoinTable(name = "book_genres",
        joinColumns = @JoinColumn(name = "isbn"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<BookGenre> genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN.equals(book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }
}
