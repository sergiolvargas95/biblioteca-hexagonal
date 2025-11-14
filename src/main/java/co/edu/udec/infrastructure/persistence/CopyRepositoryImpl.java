package co.edu.udec.infrastructure.persistence;

import co.edu.udec.domain.model.aggregates.Copy;
import co.edu.udec.domain.model.valueObjects.Isbn;
import co.edu.udec.domain.repositories.CopyRepository;
import co.edu.udec.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CopyRepositoryImpl implements CopyRepository {
    @Override
    public Copy save(Copy copy) {
        String sql = "INSERT INTO Copies (isbn, book_id, format_id, pages) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, copy.getIsbn().getValue());
            stmt.setLong(2, copy.getBookId());
            stmt.setLong(3, copy.getFormatId());
            stmt.setInt(4, copy.getPages());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Copy(
                            copy.getIsbn(),
                            copy.getBookId(),
                            copy.getFormatId(),
                            true,
                            copy.getPages(),
                            LocalDateTime.now(),
                            LocalDateTime.now()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el ejemplar: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Optional<Copy> findById(Long id) {
        String sql = "SELECT * FROM Copies WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Copy copy = mapToCopy(rs);
                return Optional.of(copy);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el ejemplar: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Copy mapToCopy(ResultSet rs) throws SQLException {
        return new Copy(
                new Isbn(rs.getString("isbn")),
                rs.getLong("book_id"),
                rs.getLong("format_id"),
                rs.getBoolean("available"),
                rs.getInt("pages"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()

        );
    }

    @Override
    public List<Copy> findAll() {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM Copies";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                copies.add(mapToCopy(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar ejemplares: " + e.getMessage(), e);
        }
        return copies;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Copies WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la copia: " + e.getMessage(), e);
        }
    }
}
