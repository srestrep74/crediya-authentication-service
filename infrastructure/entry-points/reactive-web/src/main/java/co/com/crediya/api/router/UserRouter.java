package co.com.crediya.api.router;

import co.com.crediya.api.handler.v1.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class UserRouter {

    private final UserHandler userHandler;

    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
                .POST("/users", userHandler::listenSaveUser)
                .GET("/users/{userId}/exists", userHandler::listenGetExistsUser)
                .build();
    }
}
