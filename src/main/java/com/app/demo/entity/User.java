package com.app.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(max = 100)
    @Column(name = "password")
    private String password;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @NotNull
    @Size(max = 255)
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Size(max = 255)
    @Column(name = "lastname")
    private String lastname;

    @Size(max = 255)
    @Column(name = "patronymic")
    private String patronymic;

    @NotNull
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @NotNull
    @Column(name = "age")
    private Integer age;

    @NotNull
    @Column(name = "sex")
    private Integer sex;

    @Size(max = 255)
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @OneToMany(mappedBy = "user")
    private List<Appointment> appointmentList;

    public User(String login, String password, Role role, String firstname, String lastname,
                Date birthday, Integer age, Integer sex) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.age = age;
        this.sex = sex;
        this.createDate = Calendar.getInstance().getTime();
    }
}
