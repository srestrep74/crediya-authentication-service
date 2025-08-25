package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.model.user.gateways.TransactionGateway;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

//@Slf4j
@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final TransactionGateway transactionGateway;

    public Mono<User> save(User user) {
        return transactionGateway.execute(
                userRepository.findByEmail(user.getEmail().getValue())
                        .flatMap(existingUser ->
                                Mono.<User>error (new EmailAlreadyExistsException(user.getEmail().getValue()))
                        ).switchIfEmpty(Mono.defer(() -> userRepository.save(user)))
        );
    }
}
