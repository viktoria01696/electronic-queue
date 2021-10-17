package com.messenger.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class MessageDto {

    private Long id;

    private Long studentId;

    @NotNull
    private Long chatId;

    private String messageBody;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createDate;

    private Long parentMessageId;

    private Boolean isRead;

    private Long attachmentId;

    public MessageDto(Long studentId, Long chatId) {
        this.studentId = studentId;
        this.chatId = chatId;
        this.createDate = Calendar.getInstance().getTime();
        this.isRead = false;
    }
}
