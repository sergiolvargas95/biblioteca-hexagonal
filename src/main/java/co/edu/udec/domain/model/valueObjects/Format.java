package co.edu.udec.domain.model.valueObjects;

public record Format(String name) {
    public Format {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Format name required");
        }
    }
}

