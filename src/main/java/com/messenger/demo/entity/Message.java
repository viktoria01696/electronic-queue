package com.messenger.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(name = "message_body")
    private String messageBody;

    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message parentMessage;

    @Column(name = "is_read")
    private Boolean isRead;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL)
    private Attachment attachment;

    public Message(@NotNull Student student, Chat chat) {
        this.student = student;
        this.chat = chat;
        this.createDate = Calendar.getInstance().getTime();
        this.isRead = false;
    }
}
