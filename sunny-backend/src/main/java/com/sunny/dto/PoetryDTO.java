package com.sunny.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PoetryDTO {
    private Long id;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    private String author;

    private String scene;

    private String image;

    private Integer status;

    private Integer sort;
}
