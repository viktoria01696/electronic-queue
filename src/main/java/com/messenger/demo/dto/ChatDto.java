package com.messenger.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ChatDto {

    private Long id;

    @Size(max = 255)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createDate;

    private List<Long> messageIdList;

    private List<Long> studentIdList;

    public ChatDto() {
        this.createDate = Calendar.getInstance().getTime();
    }
}
