package co.com.crediya.model.user.valueobjects;

import co.com.crediya.model.user.exception.InvalidUserDataException;

import java.math.BigDecimal;

public class Salary {
    private static final BigDecimal MIN_VALUE = new BigDecimal("0");
    private static final BigDecimal MAX_VALUE = new BigDecimal("15000000");

    private final BigDecimal value;

    private Salary(BigDecimal value) {
        this.value = value;
    }

    public static Salary of(BigDecimal value) {
        if (value == null) {
            throw new InvalidUserDataException("baseSalary cannot be null");
        }

        if (value.compareTo(MIN_VALUE) < 0) {
            throw new InvalidUserDataException("baseSalary must be greater than or equal to 0");
        }

        if (value.compareTo(MAX_VALUE) > 0) {
            throw new InvalidUserDataException("baseSalary must be less than or equal to 15000000");
        }

        return new Salary(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Salary salary = (Salary) obj;
        return value.equals(salary.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
