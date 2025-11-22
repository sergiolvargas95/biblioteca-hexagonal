package co.edu.udec.infrastructure.controllers;

import co.edu.udec.application.services.CopyApplicationService;
import co.edu.udec.domain.model.aggregates.Copy;
import co.edu.udec.domain.model.valueObjects.Isbn;

import java.util.List;

public class CopyController {
    private final CopyApplicationService copyService;

    public CopyController(CopyApplicationService copyService) {
        this.copyService = copyService;
    }

    public Copy createCopy(Long isbnValue, Long bookId, Long formatId, int pages) {
        Isbn isbn = new Isbn(String.valueOf(isbnValue));
        return copyService.createCopy(isbn, bookId, formatId, pages);
    }

    public Copy getCopy(Long isbnValue) {
        Isbn isbn = new Isbn(String.valueOf(isbnValue));
        return copyService.findByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Copia no encontrada"));
    }

    public void markAsLoaned(Long isbnValue) {
        Isbn isbn = new Isbn(String.valueOf(isbnValue));
        copyService.markAsLoaned(isbn);
    }

    public void deleteCopy(Long isbnValue) {
        Isbn isbn = new Isbn(String.valueOf(isbnValue));
        copyService.deleteByIsbn(isbn);
    }

    public Object[][] listCopies() {
        List<Copy> copies = copyService.findAll();
        Object[][] data = new Object[copies.size()][6];

        for (int i = 0; i < copies.size(); i++) {
            Copy c = copies.get(i);

            data[i][0] = c.getIsbn().getValue();
            data[i][1] = c.getBookId();
            data[i][2] = c.getFormatId();
            data[i][3] = c.getPages();
            data[i][4] = c.isAvailable() ? "Disponible" : "Prestado";
            data[i][5] = c.getCreatedAt();
        }
        return data;
    }
}
