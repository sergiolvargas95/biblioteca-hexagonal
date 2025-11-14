package co.edu.udec.infrastructure.persistence;

import co.edu.udec.domain.model.aggregates.Copy;
import co.edu.udec.domain.model.aggregates.Loan;
import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.Isbn;
import co.edu.udec.domain.model.valueObjects.LoanPeriod;
import co.edu.udec.domain.repositories.LoanRepository;
import co.edu.udec.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanRepositoryImpl implements LoanRepository {
    @Override
    public Loan save(Loan loan) {
        String sql = "INSERT INTO Loans (user_id, isbn, loan_date, return_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, loan.getUserId());
            stmt.setString(2, loan.getIsbn().value());
            stmt.setTimestamp(3, Timestamp.valueOf(loan.getPeriod().loanDate()));
            stmt.setTimestamp(4,
                    loan.getPeriod().returnDate() != null
                            ? Timestamp.valueOf(loan.getPeriod().returnDate())
                            : null
            );

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Loan(
                            keys.getLong(1),
                            loan.getUserId(),
                            loan.getIsbn(),
                            loan.getPeriod(),
                            loan.getCreatedAt(),
                            loan.getUpdatedAt(),
                            loan.isReturned()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el préstamo: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Optional<Loan> findById(Long id) {
        String sql = "SELECT * FROM Loans WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Loan loan = mapToLoan(rs);
                return Optional.of(loan);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el préstamo: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findAll() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                loans.add(mapToLoan(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar préstamos: " + e.getMessage(), e);
        }
        return loans;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Loans WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el préstamo con ID " + id + ": " + e.getMessage(), e);
        }
    }

    private Loan mapToLoan(ResultSet rs) throws SQLException {
        Long userId = rs.getLong("user_id");

        Isbn isbn = new Isbn(rs.getString("isbn"));

        LoanPeriod period = new LoanPeriod(
                rs.getTimestamp("start_date").toLocalDateTime(),
                rs.getTimestamp("end_date").toLocalDateTime()
        );

        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at") != null
                ? rs.getTimestamp("updated_at").toLocalDateTime()
                : null;

        boolean returned = rs.getBoolean("returned");

        return new Loan(
                rs.getLong("id"),
                userId,
                isbn,
                period,
                createdAt,
                updatedAt,
                returned
        );
    }
}
