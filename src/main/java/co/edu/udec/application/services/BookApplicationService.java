package co.edu.udec.application.services;

import co.edu.udec.domain.model.aggregates.Book;
import co.edu.udec.domain.model.valueObjects.Title;
import co.edu.udec.domain.repositories.AuthorRepository;
import co.edu.udec.domain.repositories.BookRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class BookApplicationService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookApplicationService(BookRepository bookRepository,
                                  AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book createBook(String title, Long authorId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("El autor no existe"));

        Book newBook = Book.createNew(
                null,
                new Title(title),
                author
        );

        return bookRepository.save(newBook);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateTitle(Long bookId, String newTitle) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        Book updatedBook = new Book(
                book.getId(),
                new Title(newTitle),
                book.getAuthor(),
                book.getCreatedAt(),
                LocalDateTime.now()
        );

        return bookRepository.update(updatedBook);
    }

    public Book changeAuthor(Long bookId, Long newAuthorId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        var newAuthor = authorRepository.findById(newAuthorId)
                .orElseThrow(() -> new IllegalArgumentException("El nuevo autor no existe"));

        Book updatedBook = new Book(
                book.getId(),
                book.getTitle(),
                newAuthor,
                book.getCreatedAt(),
                LocalDateTime.now()
        );

        return bookRepository.update(updatedBook);
    }

    public void deleteBook(Long id) {

        if (bookRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("No existe un libro con ese ID");
        }

        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
