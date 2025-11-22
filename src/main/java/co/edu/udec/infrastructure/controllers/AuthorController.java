package infraestructure.controllers;

import application.services.AuthorApplicationService;
import domain.model.aggregates.Author;
import domain.model.valueObjects.FullName;
import java.util.List;

public class AuthorController {
    private final AuthorApplicationService authorService;

    public AuthorController(AuthorApplicationService authorService) {
        this.authorService = authorService;
    }

    public String createAuthor(String firstName,
                               String middleName,
                               String lastName,
                               String secondSurname,
                               String nationality) {
        try {
            FullName fullName = new FullName(firstName, middleName, lastName, secondSurname);

            var author =  authorService.createAuthor(
                    firstName,
                    middleName,
                    lastName,
                    secondSurname,
                    nationality
            );
            return "Autor creado con ID: " + author.getId();
        } catch (Exception e) {
            return "Error creando author: " + e.getMessage();
        }
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

    public Author findByFullName(String firstName,
                                 String middleName,
                                 String lastName,
                                 String secondSurname) {

        return authorService.findByFullName(firstName, middleName, lastName, secondSurname);
    }

    public List<Author> getAllAuthors() {
        return authorService.listAuthors();
    }

}
