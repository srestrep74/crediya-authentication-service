package co.com.crediya.api.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        name = "UserExistsResponse",
        description = "Response indicating if a user exists"
)
@Builder
public record ExistsUserResponse(
        @Schema(
                description = "Whether the user exists",
                example = "true"
        )
        boolean exists,

        @Schema(
                description = "User ID that was checked",
                example = "123"
        )
        Long userId
) {}
