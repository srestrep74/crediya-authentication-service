package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<User, String>, ReactiveQueryByExampleExecutor<User> {

}
