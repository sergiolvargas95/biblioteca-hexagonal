package co.edu.udec.domain.model.entities;

import co.edu.udec.domain.model.aggregates.Book;
import co.edu.udec.domain.model.valueObjects.Isbn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Copy {
    private final Isbn isbn;
    private final String format;
    private final int pages;

    public Copy(Isbn isbn, String format, int pages) {
        if (isbn == null) throw new IllegalArgumentException("El ISBN no puede ser nulo");
        if (format == null || format.isBlank()) throw new IllegalArgumentException("El formato es obligatorio");
        if (pages <= 0) throw new IllegalArgumentException("El número de páginas debe ser mayor a cero");

        this.isbn = isbn;
        this.format = format;
        this.pages = pages;
    }
}
