package co.edu.udec.domain.repositories;

import co.edu.udec.domain.model.aggregates.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    void save(Author autor);
    Optional<Author> findById(Long id);
    List<Author> findAll();
    void update(Author autor);
    void delete(Long id);
}
