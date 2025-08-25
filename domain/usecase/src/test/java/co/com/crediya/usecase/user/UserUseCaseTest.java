package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.model.user.gateways.TransactionGateway;
import co.com.crediya.model.user.gateways.UserReactivePersistenceGateway;
import co.com.crediya.model.user.valueobjects.Email;
import co.com.crediya.model.user.valueobjects.PersonName;
import co.com.crediya.model.user.valueobjects.Salary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private UserReactivePersistenceGateway userReactivePersistenceGateway;

    @Mock
    private TransactionGateway transactionGateway;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .firstName(PersonName.of("John"))
                .lastName(PersonName.of("Doe"))
                .email(Email.of("john.doe@test.com"))
                .identityDocument("123456789")
                .phoneNumber("987654321")
                .baseSalary(Salary.of(BigDecimal.valueOf(2000)))
                .build();
    }

    @Test
    void shouldSaveUserWhenEmailDoesNotExist() {
        when(userReactivePersistenceGateway.findByEmail(user.getEmail().getValue())).thenReturn(Mono.empty());
        when(userReactivePersistenceGateway.save(user)).thenReturn(Mono.just(user));
        when(transactionGateway.execute(any())).thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(userUseCase.save(user))
                .expectNextMatches(savedUser -> savedUser.getEmail().equals(user.getEmail()))
                .verifyComplete();

        verify(userReactivePersistenceGateway).findByEmail(user.getEmail().getValue());
        verify(userReactivePersistenceGateway).save(user);
        verify(transactionGateway).execute(any());

    }

    @Test
    void shouldThrowErrorWhenEmailAlreadyExists() {
        when(userReactivePersistenceGateway.findByEmail(user.getEmail().getValue())).thenReturn(Mono.just(user));
        when(transactionGateway.execute(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(userUseCase.save(user))
                .expectErrorMatches(ex -> ex instanceof EmailAlreadyExistsException &&
                        ex.getMessage().contains(user.getEmail().getValue())
                ).verify();

        verify(userReactivePersistenceGateway).findByEmail(user.getEmail().getValue());
        verify(userReactivePersistenceGateway, never()).save(any());
    }

    @Test
    void shouldPropagateErrorWhenTransactionFails() {
        when(userReactivePersistenceGateway.findByEmail(user.getEmail().getValue())).thenReturn(Mono.empty());
        when(transactionGateway.execute(any())).thenReturn(Mono.error(new RuntimeException("Tx error")));

        StepVerifier.create(userUseCase.save(user))
                .expectErrorMatches(ex -> ex instanceof RuntimeException &&
                        ex.getMessage().equals("Tx error"))
                .verify();

        verify(userReactivePersistenceGateway).findByEmail(user.getEmail().getValue());
        verify(userReactivePersistenceGateway, never()).save(any());
        verify(transactionGateway).execute(any());
    }
}
