package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.valueobjects.Email;
import co.com.crediya.model.user.valueobjects.PersonName;
import co.com.crediya.model.user.valueobjects.Salary;
import co.com.crediya.r2dbc.adapters.user.UserReactiveRepository;
import co.com.crediya.r2dbc.adapters.user.UserReactiveRepositoryAdapter;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.mapper.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Mock
    UserMapper userMapper;

    private final String email = "john.doe@test.com.co";
    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email(email)
                .identityDocument("123456789")
                .phoneNumber("987654321")
                .baseSalary(new BigDecimal(5000000))
                .build();

        user = User.builder()
                .id(1L)
                .firstName(PersonName.of("John"))
                .lastName(PersonName.of("Doe"))
                .email(Email.of(email))
                .identityDocument("123456789")
                .phoneNumber("987654321")
                .baseSalary(Salary.of(new BigDecimal(5000000)))
                .build();
    }

    @Test
    void shouldFindByEmail() {
        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(repository.findByEmail(email)).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findByEmail(email);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getEmail().getValue().equals(email))
                .verifyComplete();
    }

    @Test
    void shouldSaveUser() {
        when(userMapper.toData(user)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(userMapper.toEntity(userEntity)).thenReturn(user);

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNextMatches(u ->
                        u.getId().equals(user.getId()) &&
                                u.getEmail().getValue().equals(user.getEmail().getValue())
                )
                .verifyComplete();
    }
}
