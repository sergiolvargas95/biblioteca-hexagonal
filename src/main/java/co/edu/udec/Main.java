package co.edu.udec;

import co.edu.udec.application.services.*;
import co.edu.udec.infrastructure.controllers.*;
import co.edu.udec.infrastructure.persistence.*;
import co.edu.udec.presentation.PrincipalView;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserRepositoryImpl userRepo = new UserRepositoryImpl();
        AuthorRepositoryImpl authorRepo = new AuthorRepositoryImpl();
        BookRepositoryImpl bookRepo = new BookRepositoryImpl();
        CopyRepositoryImpl copyRepo = new CopyRepositoryImpl();
        LoanRepositoryImpl loanRepo = new LoanRepositoryImpl();

        UserApplicationService userService = new UserApplicationService(userRepo);
        AuthorApplicationService authorService = new AuthorApplicationService(authorRepo);
        BookApplicationService bookService = new BookApplicationService(bookRepo, authorRepo);
        CopyApplicationService copyService = new CopyApplicationService(copyRepo);
        LoanApplicationService loanService = new LoanApplicationService(
                loanRepo,
                userRepo,
                copyRepo
        );

        UserController userController = new UserController(userService);
        AuthorController authorController = new AuthorController(authorService);
        BookController bookController = new BookController(bookService);
        CopyController copyController = new CopyController(copyService);
        LoanController loanController = new LoanController(loanService);

        JFrame frame = new JFrame("Biblioteca Digital");
        frame.setContentPane(new PrincipalView().MenuBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.NORMAL);

        frame.setVisible(true);


    }
}