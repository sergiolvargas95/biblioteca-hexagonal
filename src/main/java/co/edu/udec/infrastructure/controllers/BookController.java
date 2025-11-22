package infraestructure.controllers;

import application.services.BookApplicationService;
import domain.model.aggregates.Book;
import java.util.List;
import java.util.Optional;

public class BookController {

    private final BookApplicationService bookService;

    public BookController(BookApplicationService bookService) {
        this.bookService = bookService;
    }

    public void createBook(String title, Long authorId) {
        bookService.createBook(title, authorId);
    }

    public void updateTitle(Long id, String newTitle) {
        bookService.updateTitle(id, newTitle);
    }

    public void changeAuthor(Long id, Long newAuthorId) {
        bookService.changeAuthor(id, newAuthorId);
    }

    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    public Optional<Book> getBook(Long id) {
        return bookService.getBookById(id);
    }

    public Book findByTitle(String title) {
        try {
            return bookService.findByTitle(title);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el libro por título: " + e.getMessage(), e);
        }
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
