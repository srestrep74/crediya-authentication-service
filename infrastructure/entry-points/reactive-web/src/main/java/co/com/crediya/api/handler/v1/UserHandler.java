package co.com.crediya.api.handler.v1;

import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.mapper.CreateUserMapper;
import co.com.crediya.api.service.UserService;
import co.com.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CreateUserRequest.class)
                .map(CreateUserMapper::toDomain)
                .flatMap(userService::save)
                .flatMap(user -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user)
                )
                .onErrorResume(ex -> ServerResponse
                        .badRequest()
                        .bodyValue(ex.getMessage())
                );
    }
}
