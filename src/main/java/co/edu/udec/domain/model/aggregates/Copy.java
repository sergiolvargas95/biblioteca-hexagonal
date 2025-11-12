package co.edu.udec.domain.model.aggregates;

import co.edu.udec.domain.model.valueObjects.Isbn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Copy {
    private final Isbn isbn;
    private final Long bookId;
    private final Long formatId;
    private final int pages;
    private final LocalDateTime createdAt;
    private boolean available;
    private LocalDateTime updatedAt;

    public Copy(Isbn isbn, Long bookId, Long formatId, boolean available,int pages, LocalDateTime createdAt, LocalDateTime updatedAt) {

        if (isbn == null) throw new IllegalArgumentException("El ISBN no puede ser nulo");
        if (bookId == null) throw new IllegalArgumentException("El libro es obligatorio");
        if (formatId == null) throw new IllegalArgumentException("El formato es obligatorio");
        if (pages <= 0) throw new IllegalArgumentException("El número de páginas debe ser mayor a cero");

        this.isbn = isbn;
        this.bookId = bookId;
        this.formatId = formatId;
        this.available = available;
        this.pages = pages;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

    public static Copy createNew(Isbn isbn, Long bookId, Long formatId, int pages) {
        return new Copy(isbn, bookId, formatId, true, pages, LocalDateTime.now(), null);
    }

    public void markAsUnavailable() {
        if (!available) throw new IllegalStateException("La copia ya no está disponible");
        this.available = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsAvailable() {
        if (available) throw new IllegalStateException("La copia ya está disponible");
        this.available = true;
        this.updatedAt = LocalDateTime.now();
    }
}
