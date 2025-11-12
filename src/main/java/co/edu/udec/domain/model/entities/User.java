package co.edu.udec.domain.model.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private final Long id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String secondSurname;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public User(Long id, String firstName, String middleName, String lastName, String secondSurname, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondSurname = secondSurname;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
