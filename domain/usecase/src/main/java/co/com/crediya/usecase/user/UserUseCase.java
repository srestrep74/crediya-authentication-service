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
