package co.edu.udec.application.services;

import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.Email;
import co.edu.udec.domain.model.valueObjects.FullName;
import co.edu.udec.domain.repositories.UserRepository;

import java.util.Optional;

public class UserApplicationService {
    private final UserRepository userRepository;

    public UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(
            String firstName,
            String middleName,
            String lastName,
            String secondSurname,
            String email,
            String password
    ) {

        Email emailVO = new Email(email);
        Optional<User> existing = userRepository.findByEmail(emailVO);

        if (existing.isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado: " + email);
        }

        FullName fullName = new FullName(firstName, middleName, lastName, secondSurname);

        User user = User.createNew(
                null,
                fullName,
                emailVO,
                password
        );

        return userRepository.save(user);
    }

    public User updateUser(
            Long id,
            String firstName,
            String middleName,
            String lastName,
            String secondSurname,
            String email
    ) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

        FullName newFullName = new FullName(firstName, middleName, lastName, secondSurname);
        Email newEmail = new Email(email);

        existing.updateProfile(newFullName, newEmail);

        return userRepository.update(existing);
    }

    public void deleteUser(Long id) {

        if (!userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Usuario no existe: " + id);
        }

        userRepository.deleteById(id);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(new Email(email));
    }

    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }
}
