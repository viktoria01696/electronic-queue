package com.messenger.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "type")
    private String type;

    public Attachment(String url, String type) {
        this.url = url;
        this.type = type;
    }
}
