package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import reactor.core.publisher.Mono;

public interface UserReactivePersistenceGateway {
    Mono<User> save(User user);
    Mono<User> findByEmail(String email);
    Mono<Boolean> existsById(Long userId);
}
