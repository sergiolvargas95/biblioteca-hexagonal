package co.edu.udec.domain.model.entities;

import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.LoanPeriod;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Loan {
    private final Long id;
    private final Long userId;
    private final Long copyId;
    private final LoanPeriod period;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean returned;

    public Loan(Long id, Long userId, Long copyId, LoanPeriod period,
                LocalDateTime createdAt, LocalDateTime updatedAt, boolean returned) {

        if (userId == null) throw new IllegalArgumentException("El usuario es obligatorio");
        if (copyId == null) throw new IllegalArgumentException("La copia del libro es obligatoria");
        if (period == null) throw new IllegalArgumentException("El periodo del préstamo es obligatorio");

        this.id = id;
        this.userId = userId;
        this.copyId = copyId;
        this.period = period;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.returned = returned;
    }
}
