package com.messenger.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentDto {

    private Long id;

    private Long messageId;

    private Long postId;

    @NotNull
    private String url;

    @NotNull
    private String type;

    public AttachmentDto(String url, String type) {
        this.url = url;
        this.type = type;
    }
}
