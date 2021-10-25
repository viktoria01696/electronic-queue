package com.app.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class AppointmentDto {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd k:m:s:S", timezone = "Europe/Moscow")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public AppointmentDto(Date date) {
        this.date = date;
    }


}
