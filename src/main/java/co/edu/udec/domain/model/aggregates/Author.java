package co.edu.udec.domain.model.aggregates;

import co.edu.udec.domain.model.valueObjects.FullName;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Author {
    private final Long id;
    private final FullName fullName;
    private final String nationality;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Author(Long id, FullName fullName, String nationality, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (fullName == null) {
            throw new IllegalArgumentException("El nombre completo del autor es obligatorio");
        }
        if (nationality == null || nationality.isBlank()) {
            throw new IllegalArgumentException("La nacionalidad es obligatoria");
        }

        this.id = id;
        this.fullName = fullName;
        this.nationality = nationality;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();;
        this.updatedAt = updatedAt;
    }
}
