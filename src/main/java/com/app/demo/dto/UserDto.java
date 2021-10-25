package com.app.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String login;

    @NotNull
    @Size(max = 100)
    private String password;

    @NotNull
    @Size(max = 255)
    private String firstname;

    @NotNull
    @Size(max = 255)
    private String lastname;

    @Size(max = 255)
    private String patronymic;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @NotNull
    private Integer age;

    @NotNull
    private Integer sex;

    @Size(max = 255)
    @Email
    private String email;

    private List<Long> appointmentIdList;

    public UserDto(String login, String password, String firstname, String lastname,
                   Date birthday, Integer age, Integer sex) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.age = age;
        this.sex = sex;
    }
}
