package co.edu.udec.domain.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Book {
    private final Long id;
    private final String title;
    private final String autor_id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
