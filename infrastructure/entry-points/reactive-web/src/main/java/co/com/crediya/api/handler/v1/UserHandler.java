package co.com.crediya.api.handler.v1;

import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.mapper.CreateUserMapper;
import co.com.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CreateUserRequest.class)
                .map(CreateUserMapper::toDomain)
                .flatMap(userUseCase::save)
                .map(CreateUserMapper::toDto)
                .flatMap(userResponse -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse)
                );
    }
}
