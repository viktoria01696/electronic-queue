package com.messenger.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SchoolDto {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    @NotNull
    private String address;

    private List<Long> studentIdList;

    public SchoolDto(String address) {
        this.address = address;
    }
}
