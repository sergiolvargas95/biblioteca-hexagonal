package co.edu.udec.domain.model.valueObjects;

import java.time.LocalDateTime;

public record LoanPeriod(LocalDateTime loanDate, LocalDateTime returnDate) {

    public LoanPeriod {
        if (loanDate == null) {
            throw new IllegalArgumentException("La fecha de préstamo es obligatoria");
        }
        if (returnDate != null && returnDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("La fecha de devolución no puede ser anterior a la fecha de préstamo");
        }
    }

    public boolean isActive() {
        return returnDate == null || returnDate.isAfter(LocalDateTime.now());
    }
}
