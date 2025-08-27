package co.com.crediya.model.user.valueobjects;

import co.com.crediya.model.user.exception.InvalidUserDataException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SalaryTest {

    @Test
    void shouldCreateSalaryWhenValueIsValid() {
        BigDecimal value = new BigDecimal("5000000");

        Salary salary = Salary.of(value);
        assertNotNull(salary);
        assertEquals(value, salary.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> Salary.of(null)
        );
        assertEquals("baseSalary cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        BigDecimal value = new BigDecimal("-1000");

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> Salary.of(value)
        );
        assertEquals("baseSalary must be greater than or equal to 0", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsGreaterThanMax() {
        BigDecimal value = new BigDecimal("20000000");

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> Salary.of(value)
        );
        assertEquals("baseSalary must be less than or equal to 15000000", exception.getMessage());
    }

    @Test
    void shouldBeEqualWhenValuesAreSame() {
        Salary salary1 = Salary.of(new BigDecimal("1000000"));
        Salary salary2 = Salary.of(new BigDecimal("1000000"));

        assertEquals(salary1, salary2);
        assertEquals(salary1.hashCode(), salary2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenValuesAreDifferent() {
        Salary salary1 = Salary.of(new BigDecimal("1000000"));
        Salary salary2 = Salary.of(new BigDecimal("2000000"));

        assertNotEquals(salary1, salary2);
    }
}
