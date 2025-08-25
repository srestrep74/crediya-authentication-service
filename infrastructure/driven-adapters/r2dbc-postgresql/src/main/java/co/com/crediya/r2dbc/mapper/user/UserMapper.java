package co.com.crediya.r2dbc.mapper.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.valueobjects.Email;
import co.com.crediya.model.user.valueobjects.PersonName;
import co.com.crediya.model.user.valueobjects.Salary;
import co.com.crediya.r2dbc.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserEntity entity) {
        if (entity == null) return null;

        return User.builder()
                .id(entity.getId())
                .firstName(PersonName.of(entity.getFirstName()))
                .lastName(PersonName.of(entity.getLastName()))
                .email(Email.of(entity.getEmail()))
                .identityDocument(entity.getIdentityDocument())
                .phoneNumber(entity.getPhoneNumber())
                .baseSalary(Salary.of(entity.getBaseSalary()))
                .build();
    }

    public UserEntity toData(User user) {
        if (user == null) return null;

        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName().getValue())
                .lastName(user.getLastName().getValue())
                .email(user.getEmail().getValue())
                .identityDocument(user.getIdentityDocument())
                .phoneNumber(user.getPhoneNumber())
                .baseSalary(user.getBaseSalary().getValue())
                .build();
    }
}
