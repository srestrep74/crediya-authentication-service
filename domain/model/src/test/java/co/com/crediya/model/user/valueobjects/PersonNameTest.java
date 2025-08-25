package co.com.crediya.model.user.valueobjects;

import co.com.crediya.model.user.exception.InvalidUserDataException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PersonNameTest {

    @Test
    void shouldCreatePersonNameWhenValueIsValid() {
        PersonName name = PersonName.of("Sebastian");
        assertThat(name.getValue()).isEqualTo("Sebastian");
    }

    @Test
    void shouldTrimPersonName() {
        PersonName name = PersonName.of("  Sebastian  ");
        assertThat(name.getValue()).isEqualTo("Sebastian");
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> PersonName.of(null))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessage("name cannot be null or empty");
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> PersonName.of("   "))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessage("name cannot be null or empty");
    }
}
