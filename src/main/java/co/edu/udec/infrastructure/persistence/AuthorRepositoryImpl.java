package infraestructure.persistence;

import domain.model.aggregates.Author;
import domain.model.valueObjects.FullName;
import domain.repositories.AuthorRepository;
import infraestructure.config.DatabaseConfig;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {
    @Override
    public Author save(Author author) {
        String sql = "INSERT INTO Authors (first_name, middle_name, last_name, second_surname, nationality, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getFullName().getFirstName());
            stmt.setString(2, author.getFullName().getMiddleName());
            stmt.setString(3, author.getFullName().getLastName());
            stmt.setString(4, author.getFullName().getSecondSurname());
            stmt.setString(5, author.getNationality());
            stmt.setTimestamp(6, Timestamp.valueOf(author.getCreatedAt()));
            stmt.setTimestamp(7, Timestamp.valueOf(author.getUpdatedAt()));

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if(rs.next()) {
                    return new Author(
                            rs.getLong(1),
                            author.getFullName(),
                            author.getNationality(),
                            author.getCreatedAt(),
                            author.getUpdatedAt()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el autor: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<Author> findById(Long id) {
        String sql = "SELECT * FROM Authors WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Author autor = mapToAutor(rs);
                return Optional.of(autor);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el autor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Author mapToAutor(ResultSet rs)  throws SQLException {
        return new Author(
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
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Authors";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FullName fullName = new FullName(
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("last_name"),
                        rs.getString("second_surname")
                );

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

                authors.add(author);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar autores: " + e.getMessage(), e);
        }
        return authors;
    }

    @Override
    public void update(Author autor) {
        String sql = "UPDATE Authors SET first_name=?, middle_name=?, last_name=?, second_surname=?, nationality=?, updated_at=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, autor.getFullName().getFirstName());
            stmt.setString(2, autor.getFullName().getMiddleName());
            stmt.setString(3, autor.getFullName().getLastName());
            stmt.setString(4, autor.getFullName().getSecondSurname());
            stmt.setString(5, autor.getNationality());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(7, autor.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el autor: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Authors WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el autor: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Author> findByFullName(FullName fullName) {
        String sql = "SELECT * FROM Authors " +
                "WHERE first_name = ? AND " +
                "      COALESCE(middle_name, '') = COALESCE(?, '') AND " +
                "      last_name = ? AND " +
                "      COALESCE(second_surname, '') = COALESCE(?, '')";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fullName.getFirstName());
            stmt.setString(2, fullName.getMiddleName());
            stmt.setString(3, fullName.getLastName());
            stmt.setString(4, fullName.getSecondSurname());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Author author = mapToAuthor(rs);
                return Optional.of(author);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar autor por nombre completo: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    private Author mapToAuthor(ResultSet rs) throws SQLException {

        FullName fullName = new FullName(
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("second_surname")
        );

        Author author = new Author(
                rs.getLong("id"),
                fullName,
                rs.getString("nationality"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at") != null ?
                        rs.getTimestamp("updated_at").toLocalDateTime() :
                        null
        );

        return author;
    }
}