package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.model.user.gateways.TransactionGateway;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

//@Slf4j
@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final TransactionGateway transactionGateway;

    public Mono<User> register(User user) {
        /*
        return UserValidator.validate(user)
                .flatMap(validUser -> userRepository.findByEmail(validUser.getEmail())
                        .flatMap(existing -> Mono.<User>error(
                                new EmailAlreadyExistsException(validUser.getEmail())
                        ))
                        .switchIfEmpty(userRepository.save(user))
                );
                .doOnSuccess(savedUser -> log.info("User registered successfully with email: {}",
                        savedUser.getEmail()))
                .doOnError(InvalidUserDataException.class,
                        error -> log.warn("User validation failed: {}", error.getMessage()))
                .doOnError(EmailAlreadyExistsException.class,
                        error -> log.warn("Registration failed - email already exists: {}", error.getMessage()))
                .doOnError(Exception.class,
                        error -> log.error("Unexpected error during user registration", error));
                */
        return UserValidator.validate(user)
                .flatMap(validUser ->
                        transactionGateway.execute(
                                userRepository.findByEmail(user.getEmail())
                                        .flatMap(existingUser -> Mono.<User>error(
                                                new EmailAlreadyExistsException(validUser.getEmail())
                                        ))
                                        .switchIfEmpty(userRepository.save(user))
                        )
                );
    }
}
