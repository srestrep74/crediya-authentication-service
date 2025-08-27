package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.v1.CreateUserRequest;
import co.com.crediya.api.dto.v1.CreateUserResponse;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.valueobjects.Email;
import co.com.crediya.model.user.valueobjects.PersonName;
import co.com.crediya.model.user.valueobjects.Salary;
import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper {

    private CreateUserMapper() {}

    public static User toDomain(CreateUserRequest createUserRequest) {
        return User.builder()
                .firstName(PersonName.of(createUserRequest.firstName()))
                .lastName(PersonName.of(createUserRequest.lastName()))
                .email(Email.of(createUserRequest.email()))
                .identityDocument(createUserRequest.identityDocument())
                .phoneNumber(createUserRequest.phoneNumber())
                .baseSalary(Salary.of(createUserRequest.baseSalary()))
                .build();
    }

    public static CreateUserResponse toDto(User user) {
        return new CreateUserResponse(
                user.getId(),
                user.getFirstName().getValue(),
                user.getLastName().getValue(),
                user.getEmail().getValue(),
                user.getIdentityDocument(),
                user.getPhoneNumber(),
                user.getBaseSalary().getValue()
        );
    }
}
