package com.app.demo.entity;

import com.app.demo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //дата и время начала приема
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    //код подтверждения записи
    @NotNull
    @Column(name = "confirmation_code")
    @Size(max = 36)
    private String confirmationCode;

    //дата заявки на запись
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    //автор заявки
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //статус заявки
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private Status status;

    public Appointment(Date date, String confirmationCode, User user, Status status) {
        this.date = date;
        this.confirmationCode = confirmationCode;
        this.createDate = Calendar.getInstance().getTime();
        this.user = user;
        this.status = status;
    }
}
