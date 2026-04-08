package com.astrapay.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NoteRequestDto {

    @NotBlank(message = "Title tidak boleh kosong")
    private String title;

    @NotBlank(message = "Content tidak boleh kosong")
    private String content;
}
