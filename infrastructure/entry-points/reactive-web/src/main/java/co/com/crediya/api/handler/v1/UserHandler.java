package co.com.crediya.api.handler.v1;

import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.mapper.CreateUserMapper;
import co.com.crediya.model.user.User;
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

    private final UserUseCase userUseCase;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CreateUserRequest.class)
                .map(CreateUserMapper::toDomain)
                /*
                .flatMap(userUseCase::register)
                 */
                .flatMap(user -> userUseCase.register(user) // First user registration
                        .flatMap(savedUser -> {
                            // Create a new user object manually
                            User newUser = new User();
                            newUser.setFirstName("Nuevo");
                            newUser.setLastName("Usuario");
                            newUser.setEmail("juan.perez10@example.com");
                            newUser.setIdentityDocument("99999999");
                            newUser.setPhoneNumber("3009999999");
                            newUser.setBaseSalary(savedUser.getBaseSalary());

                            // Register the second user and return its Mono
                            return userUseCase.register(newUser);
                        })
                )
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
