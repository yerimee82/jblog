package com.poscodx.jblog.vo;

import lombok.Data;

@Data
public class PostVo {
    private Long no;
    private String title;
    private String contents;
    private String regDate;
    private String id;
    private Long categoryNo;
}
