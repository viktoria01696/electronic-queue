package com.messenger.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class StudentDto {

    private Long id;

    @Size(max = 100)
    @NotNull
    private String login;

    @Size(max = 100)
    @NotNull
    private String password;

    @Size(max = 255)
    @NotNull
    private String firstname;

    @Size(max = 255)
    @NotNull
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

    @NotNull
    private Long schoolId;

    private List<Long> postIdList;

    private List<Long> messageIdList;

    private List<Long> chatIdList;

    private List<Long> friendIdList;

    @Size(max = 255)
    @Email
    private String email;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createDate;

    private Boolean isActive;

    public StudentDto(String login, String password, String firstname, String lastname,
                      Date birthday, Integer age, Integer sex, Long schoolId) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.age = age;
        this.sex = sex;
        this.schoolId = schoolId;
        this.createDate = Calendar.getInstance().getTime();
        this.isActive = false;
    }
}
