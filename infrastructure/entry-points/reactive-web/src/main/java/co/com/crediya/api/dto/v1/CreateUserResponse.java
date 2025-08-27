package co.com.crediya.api.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(
        name = "CreateUserResponse",
        description = "Response returned after successfully creating a user"
)
public record CreateUserResponse(

        @Schema(description = "Unique identifier of the user", example = "550e8400-e29b-41d4-a716-446655440000")
        Long id,

        @Schema(description = "User's first name", example = "John")
        String firstName,

        @Schema(description = "User's last name", example = "Doe")
        String lastName,

        @Schema(description = "Email address", example = "john.doe@example.com")
        String email,

        @Schema(description = "Identity document number", example = "123456789")
        String identityDocument,

        @Schema(description = "Phone number", example = "+1 555 123 4567")
        String phoneNumber,

        @Schema(description = "Base salary", example = "1200.50")
        BigDecimal baseSalary
) {}
