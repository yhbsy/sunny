package com.sunny.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("poetry")
public class Poetry implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String author;

    private String scene;

    private String image;

    private LocalDateTime createTime;

    private Integer status;

    private Integer sort;
}
