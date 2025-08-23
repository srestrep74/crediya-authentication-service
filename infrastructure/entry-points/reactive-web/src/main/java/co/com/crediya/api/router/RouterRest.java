package co.com.crediya.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(
            UserRouter userRouter
    ) {
        return RouterFunctions
            .route()
            .path("/api/v1", builder -> builder
                    .add(userRouter.routes()))
            .build();
        }
}
