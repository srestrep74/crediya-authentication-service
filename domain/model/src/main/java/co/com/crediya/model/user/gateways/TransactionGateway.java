package co.com.crediya.model.user.gateways;

import reactor.core.publisher.Mono;

public interface TransactionGateway {
    <T> Mono<T> execute(Mono<T> action);
}
