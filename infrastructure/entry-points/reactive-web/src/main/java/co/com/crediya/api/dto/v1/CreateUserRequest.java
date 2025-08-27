package co.com.crediya.api.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(
        name = "CreateUserRequest",
        description = "Payload required to create a new user"
)
public record CreateUserRequest(

        @Schema(description = "User's first name", example = "John")
        String firstName,

        @Schema(description = "User's last name", example = "Doe")
        String lastName,

        @Schema(description = "Unique email address", example = "john.doe@example.com")
        String email,

        @Schema(description = "Identity document number", example = "123456789")
        String identityDocument,

        @Schema(description = "Phone number", example = "+1 555 123 4567")
        String phoneNumber,

        @Schema(description = "Base salary in the configured currency", example = "1200.50")
        BigDecimal baseSalary
) {}
