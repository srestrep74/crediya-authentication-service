package co.com.crediya.api.dto.v1;

import java.math.BigDecimal;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String identityDocument,
        String phoneNumber,
        BigDecimal baseSalary
) {}
