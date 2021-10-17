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
public class PostDto {

    private Long id;

    @NotNull
    private Long studentId;

    private String postBody;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createDate;

    private Long attachmentId;

    public PostDto(Long studentId) {
        this.studentId = studentId;
        this.createDate = Calendar.getInstance().getTime();
    }
}
