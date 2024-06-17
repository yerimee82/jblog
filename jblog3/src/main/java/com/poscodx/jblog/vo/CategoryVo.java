package com.poscodx.jblog.vo;

import lombok.Data;

@Data
public class CategoryVo {
    private Long no;
    private String name;
    private int postCount;
    private String description;
    private String id;
}
