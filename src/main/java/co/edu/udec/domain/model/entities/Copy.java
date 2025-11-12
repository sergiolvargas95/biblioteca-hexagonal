package co.edu.udec.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Copy {
    private final String isbn;
    private final Copy copy;
    private final Format format;
    private final int pages;

    public Copy(String isbn, Copy copy, Format format, int pages) {
        this.isbn = isbn;
        this.copy = copy;
        this.format = format;
        this.pages = pages;
    }
}
