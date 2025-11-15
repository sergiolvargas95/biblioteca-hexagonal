package co.edu.udec.application.services;

import co.edu.udec.domain.model.aggregates.Author;
import co.edu.udec.domain.model.valueObjects.FullName;
import co.edu.udec.domain.repositories.AuthorRepository;

import java.util.List;

public class AuthorApplicationService {
    private final AuthorRepository authorRepository;

    public AuthorApplicationService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(String firstName,
                               String middleName,
                               String lastName,
                               String secondSurname,
                               String nationality) {

        FullName fullName = new FullName(firstName, middleName, lastName, secondSurname);
        Author newAuthor = Author.createNew(null, fullName, nationality);

        authorRepository.save(newAuthor);
        return newAuthor;
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));
    }

    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    public Author updateAuthor(Long id,
                               String firstName,
                               String middleName,
                               String lastName,
                               String secondSurname,
                               String nationality) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));

        FullName nuevoNombre = new FullName(firstName, middleName, lastName, secondSurname);

        author.setFullName(nuevoNombre);
        author.setNationality(nationality);

        authorRepository.update(author);
        return author;
    }

    public void deleteAuthor(Long id) {
        authorRepository.delete(id);
    }
}
