package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.dto.v1.CreateUserResponse;
import co.com.crediya.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper {

    private CreateUserMapper() {}

    public static User toDomain(CreateUserRequest createUserRequest) {
        return User.builder()
                .firstName(createUserRequest.firstName())
                .lastName(createUserRequest.lastName())
                .email(createUserRequest.email())
                .identityDocument(createUserRequest.identityDocument())
                .phoneNumber(createUserRequest.phoneNumber())
                .baseSalary(createUserRequest.baseSalary())
                .build();
    }

    public static CreateUserResponse toDto(User user) {
        return new CreateUserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getIdentityDocument(),
                user.getPhoneNumber(),
                user.getBaseSalary()
        );
    }
}
