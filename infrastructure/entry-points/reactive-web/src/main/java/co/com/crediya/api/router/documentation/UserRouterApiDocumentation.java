package co.com.crediya.api.router.documentation;

import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.dto.v1.CreateUserResponse;
import co.com.crediya.api.handler.v1.UserHandler;
import co.com.crediya.api.router.UserRouter;
import io.swagger.v3.oas.annotations.Operation;
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
                                            description = "Bad Request - Invalid input data"
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
