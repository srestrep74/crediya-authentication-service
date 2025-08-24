package co.com.crediya.api.service;

import co.com.crediya.model.user.User;
import co.com.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TransactionalOperator transactionalOperator;
    private final UserUseCase userUseCase;

    public Mono<Void> save(User user) {
        return transactionalOperator.execute( status ->
                userUseCase.register(user)
        ).then();
    }
}
