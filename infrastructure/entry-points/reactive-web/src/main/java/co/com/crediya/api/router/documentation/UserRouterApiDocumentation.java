package co.com.crediya.api.router.documentation;

import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.dto.v1.CreateUserResponse;
import co.com.crediya.api.dto.v1.ExistsUserResponse;
import co.com.crediya.api.handler.v1.UserHandler;
import co.com.crediya.api.helpers.ApiStandardError;
import co.com.crediya.api.router.UserRouter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouterApiDocumentation {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/users",
                    method = RequestMethod.POST,
                    beanClass = UserHandler.class,
                    beanMethod = "listenSaveUser",
                    operation = @Operation(
                            summary = "Create a new user",
                            description = "Creates a new user account in the Crediya authentication platform",
                            operationId = "createUser",
                            tags = {"User Management"},
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CreateUserRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "User created successfully",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = CreateUserResponse.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad Request - Invalid input data",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ApiStandardError.class),
                                                    examples = @ExampleObject(
                                                            name = "InvalidInput",
                                                            value = "{ \"timestamp\": \"2025-08-25T19:00:00\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"Email is invalid\", \"path\": \"/api/v1/users\" }"
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "409",
                                            description = "Conflict - Email already exists",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ApiStandardError.class),
                                                    examples = @ExampleObject(
                                                            name = "EmailConflict",
                                                            value = "{ \"timestamp\": \"2025-08-25T19:00:00\", \"status\": 409, \"error\": \"Conflict\", \"message\": \"Email already exists\", \"path\": \"/api/v1/users\" }"
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal Server Error"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/users/{userId}/exists",
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "listenGetExistsUser",
                    operation = @Operation(
                            summary = "Check if a user exists",
                            description = "Returns whether a user with the given ID exists in the system",
                            operationId = "existsUser",
                            tags = {"User Management"},
                            parameters = {
                                    @Parameter(
                                            name = "userId",
                                            description = "ID of the user to check",
                                            required = true,
                                            in = ParameterIn.PATH,
                                            example = "1"
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "User existence returned successfully",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ExistsUserResponse.class),
                                                    examples = @ExampleObject(
                                                            name = "UserExists",
                                                            value = "{ \"userId\": 1, \"exists\": true }"
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "User not found",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ApiStandardError.class),
                                                    examples = @ExampleObject(
                                                            name = "UserNotFound",
                                                            value = "{ \"timestamp\": \"2025-08-25T19:00:00\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"User with id 1 not found\", \"path\": \"/api/v1/users/1/exists\" }"
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal Server Error"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> userRouterDocumentation(UserRouter userRouter) {
        return userRouter.routes();
    }

}
