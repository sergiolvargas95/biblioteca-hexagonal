package co.edu.udec.application.services;

import co.edu.udec.domain.model.aggregates.Copy;
import co.edu.udec.domain.model.aggregates.Loan;
import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.LoanPeriod;
import co.edu.udec.domain.repositories.CopyRepository;
import co.edu.udec.domain.repositories.LoanRepository;
import co.edu.udec.domain.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class LoanApplicationService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;

    public LoanApplicationService(
            LoanRepository loanRepository,
            UserRepository userRepository,
            CopyRepository copyRepository
    ) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.copyRepository = copyRepository;
    }

    public Loan createLoan(Long userId, Long copyId, LocalDateTime loanDate, LocalDateTime returnDate) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Copy copy = copyRepository.findByIsbn(copyId)
                .orElseThrow(() -> new IllegalArgumentException("Ejemplar no encontrado"));

        if (!copy.isAvailable()) {
            throw new IllegalStateException("El ejemplar no está disponible");
        }

        LoanPeriod period = new LoanPeriod(loanDate, returnDate);

        Loan loan = new Loan(null, user.getId(), copy.getIsbn(), period, LocalDateTime.now(), null, false);

        copy.markAsLoaned();
        copyRepository.update(copy);

        return loanRepository.save(loan);
    }

    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (!loan.getPeriod().isActive()) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }

        LoanPeriod updatedPeriod = new LoanPeriod(
                loan.getPeriod().loanDate(),
                LocalDateTime.now()
        );

        loan.setPeriod(updatedPeriod);
        loanRepository.update(loan);

        Copy copy = copyRepository.findByIsbn(Long.valueOf(loan.getIsbn().getValue()))
                .orElseThrow(() -> new IllegalArgumentException("No existe la copia con ese ISBN"));

        copy.markAsAvailable();
        copyRepository.update(copy);

        return loan;
    }

    public Loan findById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public void delete(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (loan.getPeriod().isActive()) {
            Copy copy = copyRepository.findByIsbn(Long.valueOf(loan.getIsbn().getValue()))
                    .orElseThrow(() -> new IllegalArgumentException("No existe la copia con ese ISBN"));

            copy.markAsAvailable();
            copyRepository.update(copy);
        }

        loanRepository.deleteById(id);
    }
}
