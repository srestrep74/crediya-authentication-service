package co.com.crediya.model.user.valueobjects;

import co.com.crediya.model.user.exception.InvalidUserDataException;

public class PersonName {
    private final String value;

    private PersonName(String value) {
        this.value = value.trim();
    }

    public static PersonName of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidUserDataException("name cannot be null or empty");
        }

        return new PersonName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PersonName name = (PersonName) obj;
        return value.equals(name.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
