package co.edu.udec.domain.repositories;

import co.edu.udec.domain.model.aggregates.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    List<Loan> findAll();
    void deleteById(Long id);
    void update(Loan loan);
}
