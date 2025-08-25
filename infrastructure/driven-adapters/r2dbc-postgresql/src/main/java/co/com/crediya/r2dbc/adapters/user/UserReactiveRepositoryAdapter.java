package co.com.crediya.r2dbc.adapters.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.mapper.user.UserMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    User, UserEntity, Long, UserReactiveRepository
> implements UserRepository {

    private final UserMapper userMapper;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, UserMapper userMapper) {
        super(repository, mapper, entity -> mapper.map(entity, User.class));
        this.userMapper = userMapper;
    }

    @Override
    public Mono<User> save(User user) {
        return super.save(user);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(super::toEntity);
    }

    @Override
    public UserEntity toData(User user) {
        return userMapper.toData(user);
    }

    @Override
    public User toEntity(UserEntity userEntity) {
        return userMapper.toEntity(userEntity);
    }
}
