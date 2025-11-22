package domain.repositories;

import domain.model.aggregates.Author;
import domain.model.valueObjects.FullName;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author autor);
    Optional<Author> findById(Long id);
    List<Author> findAll();
    void update(Author autor);
    void delete(Long id);
    public Optional<Author> findByFullName(FullName fullName);
}

