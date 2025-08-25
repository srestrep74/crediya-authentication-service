package co.com.crediya.api;

import co.com.crediya.api.config.ExceptionConfig;
import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.dto.v1.CreateUserResponse;
import co.com.crediya.api.handler.v1.UserHandler;
import co.com.crediya.api.mapper.CreateUserMapper;
import co.com.crediya.api.router.RouterRest;
import co.com.crediya.api.router.UserRouter;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.model.user.exception.InvalidUserDataException;
import co.com.crediya.usecase.user.UserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(
        classes = {
                RouterRest.class,
                UserRouter.class,
                UserHandler.class,
                ExceptionConfig.class
        })
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    UserUseCase userUseCase;

    private CreateUserRequest validRequest;
    private CreateUserResponse expectedValidResponse;
    private CreateUserRequest invalidSalaryUserRequest;
    private CreateUserRequest invalidEmailUserRequest;

    private final String users = "/api/v1/users";

    @BeforeEach
    void setUp() {
        validRequest = new CreateUserRequest(
                "John",
                "Doe",
                "john.doe@test.com.co",
                "123456789",
                "987654321",
                new BigDecimal(5000000)
        );

        expectedValidResponse = new CreateUserResponse(
                1L,
                "John",
                "Doe",
                "john.doe@test.com.co",
                "123456789",
                "987654321",
                new BigDecimal(5000000)
        );

        invalidSalaryUserRequest = new CreateUserRequest(
                "John",
                "Doe",
                "john.doe@test.com.co",
                "123456789",
                "987654321",
                new BigDecimal(-10000)
        );

        invalidEmailUserRequest = new CreateUserRequest(
                "John",
                "Doe",
                "john.doe---test.com.co",
                "123456789",
                "987654321",
                new BigDecimal(-10000)
        );

    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(userUseCase.save(any()))
                .thenReturn(Mono.just(CreateUserMapper.toDomain(validRequest)));

        webTestClient.post()
                .uri(users)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateUserResponse.class)
                .value(userResponse -> {
                    Assertions.assertThat(userResponse.email()).isEqualTo(expectedValidResponse.email());
                    Assertions.assertThat(userResponse.firstName()).isEqualTo(expectedValidResponse.firstName());
                    Assertions.assertThat(userResponse.lastName()).isEqualTo(expectedValidResponse.lastName());
                    Assertions.assertThat(userResponse.identityDocument()).isEqualTo(expectedValidResponse.identityDocument());
                });
    }


    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() {
        when(userUseCase.save(any()))
                .thenReturn(Mono.error(new EmailAlreadyExistsException(validRequest.email())));

        webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validRequest)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.error").isEqualTo("EmailAlreadyExistsException")
                .jsonPath("$.message").isEqualTo("Email not available: " + validRequest.email());
    }

    @Test
    void shouldReturnBadRequestWhenSalaryIsNegative() {
        when(userUseCase.save(any()))
                .thenReturn(Mono.error(new InvalidUserDataException("baseSalary must be greater than or equal to 0")));

        webTestClient.post()
                .uri(users)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidSalaryUserRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("InvalidUserDataException")
                .jsonPath("$.message").isEqualTo("baseSalary must be greater than or equal to 0");
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() {
        when(userUseCase.save(any()))
                .thenReturn(Mono.error(new InvalidUserDataException("email must have a valid format")));

        webTestClient.post()
                .uri(users)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidEmailUserRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("InvalidUserDataException")
                .jsonPath("$.message").isEqualTo("email must have a valid format");
    }
}
