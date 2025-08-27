package co.com.crediya.model.user;

import co.com.crediya.model.user.valueobjects.Email;
import co.com.crediya.model.user.valueobjects.PersonName;
import co.com.crediya.model.user.valueobjects.Salary;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private PersonName firstName;
    private PersonName lastName;
    private Email email;
    private String identityDocument;
    private String phoneNumber;
    private Salary baseSalary;
}
