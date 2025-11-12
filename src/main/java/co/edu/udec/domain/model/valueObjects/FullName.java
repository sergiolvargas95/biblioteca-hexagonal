package co.edu.udec.domain.model.valueObjects;

import java.util.Objects;
import java.util.stream.Stream;

public record FullName(String firstName, String middleName, String lastName, String secondSurname) {

    public FullName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("El primer nombre es obligatorio");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("El primer apellido es obligatorio");
        }
    }

    public String getFullName() {
        return String.join(" ",
                Stream.of(firstName, middleName, lastName, secondSurname)
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isBlank())
                        .toList()
        );
    }
}
