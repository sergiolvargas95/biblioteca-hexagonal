package co.edu.udec.domain.model.aggregates;

import co.edu.udec.domain.model.valueObjects.Title;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Book {
    private final Long id;
    private final Title title;
    private final Author author;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<Copy> copies = new ArrayList<>();

    public Book(Long id, Title title, Author author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (title == null) throw new IllegalArgumentException("El título es obligatorio");
        if (author == null) throw new IllegalArgumentException("El autor es obligatorio");

        this.id = id;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

    public static Book createNew(Long id, Title title, Author author) {
        return new Book(id, title, author, LocalDateTime.now(), null);
    }

    public void addCopy(Copy copy) {
        if (copy == null) throw new IllegalArgumentException("La copia no puede ser nula");
        this.copies.add(copy);
        this.updatedAt = LocalDateTime.now();
    }
}
