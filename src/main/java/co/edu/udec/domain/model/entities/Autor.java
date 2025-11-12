package co.edu.udec.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Autor {
    private final Long id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String secondSurname;
    private final String nationality;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Autor(Long id, String firstName, String middleName, String lastName, String secondSurname, String nationality, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondSurname = secondSurname;
        this.nationality = nationality;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
