package com.messenger.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chat")
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @Size(max = 255)
    @Column(name = "chat_name")
    private String name;

    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messageList;

    @ManyToMany
    @JoinTable(name = "student_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> studentList;

    public Chat() {
        this.createDate = Calendar.getInstance().getTime();
    }
}
