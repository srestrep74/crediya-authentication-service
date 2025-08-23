package co.com.crediya.model.user.validators;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.InvalidUserDataException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class UserValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final BigDecimal MIN_SALARY = new BigDecimal("0");
    private static final BigDecimal MAX_SALARY = new BigDecimal("15000000");

    private UserValidator() {}

    public static Mono<User> validate(User user) {
        return Mono.fromCallable(() -> {
            List<String> errors = new ArrayList<>();

            validateRequiredFields(user, errors);
            validateEmailFormat(user.getEmail(), errors);
            validateSalaryRange(user.getBaseSalary(), errors);

            if (!errors.isEmpty()){
                throw new InvalidUserDataException("Validation failed: " + String.join(", ", errors));
            }

            return user;
        });
    }

    private static void validateRequiredFields(User user, List<String> errors) {
        if (isNullOrBlank(user.getFirstName())) {
            errors.add("firstName cannot be null or empty");
        }

        if (isNullOrBlank(user.getLastName())) {
            errors.add("lastName cannot be null or empty");
        }

        if (isNullOrBlank(user.getEmail())) {
            errors.add("email cannot be null or empty");
        }

        if (user.getBaseSalary() == null) {
            errors.add("baseSalary cannot be null");
        }
    }

    private static void validateEmailFormat(String email, List<String> errors) {
        if (!isNullOrBlank(email) && !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            errors.add("email must have a valid format");
        }
    }

    private static void validateSalaryRange(BigDecimal baseSalary, List<String> errors) {
        if (baseSalary != null) {
            if (baseSalary.compareTo(MIN_SALARY) < 0) {
                errors.add("baseSalary must be greater than or equal to 0");
            }

            if (baseSalary.compareTo(MAX_SALARY) > 0) {
                errors.add("baseSalary must be less than or equal to 15000000");
            }
        }
    }

    private static boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
