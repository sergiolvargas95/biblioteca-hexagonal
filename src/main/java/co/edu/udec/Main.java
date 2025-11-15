package co.edu.udec;

import co.edu.udec.application.services.*;
import co.edu.udec.domain.model.aggregates.Author;
import co.edu.udec.domain.model.aggregates.Book;
import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.Email;
import co.edu.udec.domain.model.valueObjects.FullName;
import co.edu.udec.domain.model.valueObjects.Title;
import co.edu.udec.domain.repositories.AuthorRepository;
import co.edu.udec.domain.repositories.BookRepository;
import co.edu.udec.domain.repositories.UserRepository;

import co.edu.udec.infrastructure.config.DatabaseConfig;
import co.edu.udec.infrastructure.controllers.*;
import co.edu.udec.infrastructure.persistence.*;

import java.time.LocalDateTime;
import java.util.Optional;

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
        
    }
}