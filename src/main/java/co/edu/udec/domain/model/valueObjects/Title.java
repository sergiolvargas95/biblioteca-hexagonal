package co.edu.udec.domain.model.valueObjects;

public record Title(String value) {

    public Title {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío");
        }
        if (value.length() > 255) {
            throw new IllegalArgumentException("El título no puede tener más de 255 caracteres");
        }
    }
}
