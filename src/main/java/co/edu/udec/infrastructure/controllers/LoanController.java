package co.edu.udec.infrastructure.controllers;

import co.edu.udec.application.services.LoanApplicationService;
import co.edu.udec.domain.model.aggregates.Loan;

import java.time.LocalDateTime;
import java.util.List;

public class LoanController {
    private final LoanApplicationService loanService;

    public LoanController(LoanApplicationService loanService) {
        this.loanService = loanService;
    }

    public void createLoan(Long userId, Long copyId, LocalDateTime loanDate, LocalDateTime returnDate) {
        loanService.createLoan(userId, copyId, loanDate, returnDate);
    }

    public void returnLoan(Long loanId) {
        loanService.returnLoan(loanId);
    }

    public Loan getLoan(Long id) {
        return loanService.findById(id);
    }

    public void deleteLoan(Long id) {
        loanService.delete(id);
    }

    public Object[][] listLoans() {
        List<Loan> loans = loanService.findAll();
        Object[][] data = new Object[loans.size()][6];

        for (int i = 0; i < loans.size(); i++) {
            Loan l = loans.get(i);

            data[i][0] = l.getId();
            data[i][1] = l.getUserId();
            data[i][2] = l.getIsbn().getValue();
            data[i][3] = l.getPeriod().loanDate();
            data[i][4] = l.getPeriod().returnDate() != null ? l.getPeriod().returnDate() : "No devuelto";
            data[i][5] = l.isReturned() ? "Sí" : "No";
        }
        return data;
    }
}
