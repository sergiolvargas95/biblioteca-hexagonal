package co.edu.udec.application.services;

import co.edu.udec.domain.model.aggregates.Copy;
import co.edu.udec.domain.model.valueObjects.Isbn;
import co.edu.udec.domain.repositories.CopyRepository;

import java.util.List;
import java.util.Optional;

public class CopyApplicationService {
    private final CopyRepository copyRepository;

    public CopyApplicationService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public Copy createCopy(Isbn isbn, Long bookId, Long formatId, int pages) {
        Copy newCopy = Copy.createNew(isbn, bookId, formatId, pages);
        return copyRepository.save(newCopy);
    }

    public Optional<Copy> findByIsbn(Isbn isbn) {
        return copyRepository.findByIsbn(Long.valueOf(isbn.getValue()));
    }

    public List<Copy> findAll() {
        return copyRepository.findAll();
    }

    public void markAsLoaned(Isbn isbn) {
        Copy copy = copyRepository.findByIsbn(Long.valueOf(isbn.getValue()))
                .orElseThrow(() -> new RuntimeException("Copia no encontrada: " + isbn.value()));

        copy.markAsLoaned();
        copyRepository.update(copy);
    }

    public void deleteByIsbn(Isbn isbn) {
        copyRepository.deleteById(Long.valueOf(isbn.getValue()));
    }
}
