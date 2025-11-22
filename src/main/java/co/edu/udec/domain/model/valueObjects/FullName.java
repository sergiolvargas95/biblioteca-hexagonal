package co.edu.udec.domain.model.valueObjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullName {

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String secondSurname;

    public FullName(String firstName, String middleName, String lastName, String secondSurname) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("El primer nombre no puede estar vacío");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("El primer apellido no puede estar vacío");
        }

        this.firstName = firstName.trim();
        this.middleName = (middleName == null) ? "" : middleName.trim();
        this.lastName = lastName.trim();
        this.secondSurname = (secondSurname == null) ? "" : secondSurname.trim();
    }

    public String getFullName() {
        return String.join(" ",
                firstName,
                middleName.isEmpty() ? "" : middleName,
                lastName,
                secondSurname.isEmpty() ? "" : secondSurname
        ).trim();
    }
}