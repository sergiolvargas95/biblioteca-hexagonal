package co.edu.udec.infrastructure.controllers;

import co.edu.udec.application.services.BookApplicationService;
import co.edu.udec.domain.model.aggregates.Book;

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

    public Object[][] listBooks() {
        List<Book> books = bookService.getAllBooks();
        Object[][] data = new Object[books.size()][4];

        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            data[i][0] = b.getId();
            data[i][1] = b.getTitle();
            data[i][2] = b.getAuthor();
        }

        return data;
    }
}
