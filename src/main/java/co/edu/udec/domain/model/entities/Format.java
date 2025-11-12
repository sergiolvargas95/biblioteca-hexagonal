package co.edu.udec.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Format {
    private final Long id;
    private final String name;

    public Format(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
