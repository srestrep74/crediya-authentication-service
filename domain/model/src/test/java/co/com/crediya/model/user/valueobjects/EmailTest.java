package co.com.crediya.model.user.valueobjects;

import co.com.crediya.model.user.exception.InvalidUserDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = Email.of("test@example.com");
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void shouldTrimEmailBeforeCreation() {
        Email email = Email.of("   test@example.com   ");
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> Email.of(null)
        );
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> Email.of("   ")
        );
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailHasInvalidFormat() {
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> Email.of("invalid-email")
        );
        assertEquals("email must have a valid format", exception.getMessage());
    }

    @Test
    void shouldNotConsiderDifferentEmailsEqual() {
        Email email1 = Email.of("test@example.com");
        Email email2 = Email.of("other@example.com");

        assertNotEquals(email1, email2);
    }
}
