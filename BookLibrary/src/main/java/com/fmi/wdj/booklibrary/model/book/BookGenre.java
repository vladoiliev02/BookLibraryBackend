package com.fmi.wdj.booklibrary.model.book;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "genres", indexes = {@Index(columnList = "genre_name")})
@NoArgsConstructor
@Data
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "genre_id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre_name")
    private Genre genre;

    public BookGenre(Genre genre) {
        this.genre = genre;
    }
}
