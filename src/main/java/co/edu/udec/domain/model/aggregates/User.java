package co.edu.udec.domain.model.aggregates;

import co.edu.udec.domain.model.valueObjects.Email;
import co.edu.udec.domain.model.valueObjects.FullName;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private final Long id;
    private final FullName fullName;
    private final Email email;
    private String password;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(Long id, FullName fullName, Email email, String password,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (fullName == null) throw new IllegalArgumentException("El nombre completo es obligatorio");
        if (email == null) throw new IllegalArgumentException("El email es obligatorio");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("La contraseña no puede estar vacía");

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
    }
}
