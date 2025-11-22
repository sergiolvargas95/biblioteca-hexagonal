package domain.repositories;

import domain.model.aggregates.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    void deleteById(Long id);
    Book update(Book updatedBook);
    Optional<Book> findByTitle(String title);
}
