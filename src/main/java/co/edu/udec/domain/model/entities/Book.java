package co.edu.udec.domain.model.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class Book {
    private final Long id;
    private final String title;
    private final Autor autor;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Book(LocalDateTime updatedAt, LocalDateTime createdAt, String title, Long id, Autor autor) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.title = title;
        this.id = id;
        this.autor = autor;
    }
}
