package co.edu.udec.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Loan {
    private final Long id;
    private final User user;
    private final Copy copy;
    private final LocalDateTime loanDate;
    private final LocalDateTime returnDate;

    public Loan(Long id, User user, Copy copy, LocalDateTime loanDate, LocalDateTime returnDate) {
        this.id = id;
        this.user = user;
        this.copy = copy;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
}
