package com.messenger.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @NotNull
    private Student student;

    @Column(name = "post_body")
    private String postBody;

    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Attachment attachment;

    public Post(Student student) {
        this.student = student;
        this.createDate = Calendar.getInstance().getTime();
    }
}
