package infraestructure.persistence;

import domain.model.aggregates.Author;
import domain.model.aggregates.Book;
import domain.model.valueObjects.FullName;
import domain.model.valueObjects.Title;
import domain.repositories.BookRepository;
import infraestructure.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {
    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO Books (title, author_id, created_at, updated_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle().getValue());
            stmt.setLong(2, book.getAuthor().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(book.getCreatedAt()));
            stmt.setTimestamp(4, Timestamp.valueOf(book.getUpdatedAt()));

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Book(
                            keys.getLong(1),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCreatedAt(),
                            book.getUpdatedAt()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el libro: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM Books WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Long authorId = rs.getLong("author_id");

                AuthorRepositoryImpl autorRepo = new AuthorRepositoryImpl();
                Optional<Author> autorOpt = autorRepo.findById(authorId);

                if (autorOpt.isEmpty()) {
                    throw new RuntimeException("No se encontró el autor con ID: " + authorId);
                }

                Author author = autorOpt.get();

                Book book = new Book(
                        rs.getLong("id"),
                        new Title(rs.getString("title")),
                        author,
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el libro: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = """
           SELECT 
               b.id,
               b.title,
               b.author_id,
               b.created_at AS book_created,
               b.updated_at AS book_updated,

               a.id AS author_id,
               a.first_name,
               a.middle_name,
               a.last_name,
               a.second_surname,
               a.nationality,
               a.created_at,
               a.updated_at

           FROM Books b JOIN Authors a ON b.author_id = a.id
       """;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Author author = new Author(
                        rs.getLong("id"),
                        new FullName(
                                rs.getString("first_name"),
                                rs.getString("middle_name"),
                                rs.getString("last_name"),
                                rs.getString("second_surname")
                        ),
                        rs.getString("nationality"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );

                books.add(new Book(
                        rs.getLong("id"),
                        new Title(rs.getString("title")),
                        author,
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar libros: " + e.getMessage(), e);
        }

        return books;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public Book update(Book updatedBook) {
        String sql = "UPDATE Books SET title = ?, author_id = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updatedBook.getTitle().value());
            stmt.setLong(2, updatedBook.getAuthor().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(updatedBook.getUpdatedAt()));
            stmt.setLong(4, updatedBook.getId());

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No existe el libro con ID: " + updatedBook.getId());
            }

            return updatedBook;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el libro: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        String sql = """
           SELECT 
               b.id,
               b.title,
               b.author_id,
               b.created_at AS book_created,
               b.updated_at AS book_updated,

               a.id AS author_id,
               a.first_name,
               a.middle_name,
               a.last_name,
               a.second_surname,
               a.nationality,
               a.created_at,
               a.updated_at

           FROM Books b
           JOIN Authors a ON b.author_id = a.id
           WHERE b.title = ?
       """;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Author author = new Author(
                            rs.getLong("id"),
                            new FullName(
                                    rs.getString("first_name"),
                                    rs.getString("middle_name"),
                                    rs.getString("last_name"),
                                    rs.getString("second_surname")
                            ),
                            rs.getString("nationality"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );

                    Book book = new Book(
                            rs.getLong("id"),
                            new Title(rs.getString("title")),
                            author,
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );

                    return Optional.of(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar libro por título: " + e.getMessage(), e);
        }

        return null;
    }

}
