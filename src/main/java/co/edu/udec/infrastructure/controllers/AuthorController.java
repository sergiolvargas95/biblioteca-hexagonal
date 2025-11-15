package co.edu.udec.infrastructure.controllers;

import co.edu.udec.application.services.AuthorApplicationService;
import co.edu.udec.domain.model.aggregates.Author;

import java.util.List;

public class AuthorController {
    private final AuthorApplicationService authorService;

    public AuthorController(AuthorApplicationService authorService) {
        this.authorService = authorService;
    }

    public Author createAuthor(String firstName,
                               String middleName,
                               String lastName,
                               String secondSurname,
                               String nationality) {

        return authorService.createAuthor(
                firstName,
                middleName,
                lastName,
                secondSurname,
                nationality
        );
    }

    public Author getAuthorById(Long id) {
        return authorService.findById(id);
    }


    public Author updateAuthor(Long id,
                               String firstName,
                               String middleName,
                               String lastName,
                               String secondSurname,
                               String nationality) {

        return authorService.updateAuthor(
                id,
                firstName,
                middleName,
                lastName,
                secondSurname,
                nationality
        );
    }


    public void deleteAuthor(Long id) {
        authorService.deleteAuthor(id);
    }

    public Object[][] listAuthors() {
        List<Author> authors = authorService.listAuthors();
        Object[][] data = new Object[authors.size()][3];

        for (int i = 0; i < authors.size(); i++) {
            Author a = authors.get(i);
            data[i][0] = a.getId();
            data[i][1] = a.getFullName().getFirstName() + " " +
                    a.getFullName().getMiddleName() + " " +
                    a.getFullName().getLastName() + " " +
                    a.getFullName().getSecondSurname();
            data[i][2] = a.getNationality();
        }

        return data;
    }

}
