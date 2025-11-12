package co.edu.udec.domain.model.valueObjects;

public record Isbn(String value) {
    public Isbn {
        if (value == null || !value.matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("Invalid ISBN");
        }
    }
}
