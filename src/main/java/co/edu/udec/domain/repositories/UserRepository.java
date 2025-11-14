package co.edu.udec.domain.repositories;


import co.edu.udec.domain.model.aggregates.User;
import co.edu.udec.domain.model.valueObjects.Email;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(Email email);
    List<User> findAll();
    void deleteById(Long id);
}
