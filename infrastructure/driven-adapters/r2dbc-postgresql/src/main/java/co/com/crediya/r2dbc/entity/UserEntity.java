package co.com.crediya.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {

    @Id
    @Column("user_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String identityDocument;
    private String phoneNumber;
    private BigDecimal baseSalary;
}
