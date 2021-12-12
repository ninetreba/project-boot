package com.accenture.russiaatc.irentservice10.SNAPSHOT.model.user;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.Status;
import com.accenture.russiaatc.irentservice10.SNAPSHOT.model.dto.UserDto;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "USER", schema = "PUBLIC")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static UserDto toUserDto (User user){
        UserDto userDto = new UserDto();
        userDto.setBalance(user.getBalance());
        userDto.setLogin(user.getLogin());
        userDto.setId(user.getId());
        return userDto;
    }


}
