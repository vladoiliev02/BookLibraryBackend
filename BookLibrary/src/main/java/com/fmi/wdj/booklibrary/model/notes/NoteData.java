package com.fmi.wdj.booklibrary.model.notes;

import com.fmi.wdj.booklibrary.model.user.User;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "note_data", indexes = {@Index(columnList = "owner_username")})
public class NoteData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "noteData")
    private List<Note> notes;

    public NoteData() {
    }

    public NoteData(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteData noteData = (NoteData) o;
        return id.equals(noteData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NoteData{" +
            "id=" + id +
            '}';
    }
}
