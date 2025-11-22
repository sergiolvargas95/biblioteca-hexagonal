package co.edu.udec.domain.model.aggregates;

import co.edu.udec.domain.model.valueObjects.Isbn;
import co.edu.udec.domain.model.valueObjects.LoanPeriod;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Loan {
    private final Long id;
    private final Long userId;
    private final Isbn isbn;
    private LoanPeriod period;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean returned;

    public Loan(Long id, Long userId, Isbn isbn, LoanPeriod period,
                LocalDateTime createdAt, LocalDateTime updatedAt, boolean returned) {

        if (userId == null) throw new IllegalArgumentException("El usuario es obligatorio");
        if (isbn == null) throw new IllegalArgumentException("La copia del libro es obligatoria");
        if (period == null) throw new IllegalArgumentException("El periodo del préstamo es obligatorio");

        this.id = id;
        this.userId = userId;
        this.isbn = isbn;
        this.period = period;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.returned = returned;
    }

    public static Loan createNew(Long id, Long userId, Isbn isbn, LoanPeriod period) {
        return new Loan(id, userId, isbn, period, LocalDateTime.now(), null, false);
    }

    public void markAsReturned() {
        if (returned) {
            throw new IllegalStateException("El préstamo ya fue marcado como devuelto");
        }
        this.returned = true;
        this.updatedAt = LocalDateTime.now();
    }
}
