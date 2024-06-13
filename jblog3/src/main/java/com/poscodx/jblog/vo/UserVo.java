package com.poscodx.jblog.vo;

import com.poscodx.jblog.validation.Unique;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class UserVo {
    @NotEmpty
    @Unique(field = "id")
    @Length(min = 4, max = 12)
    private String id;

    @NotEmpty
    @Unique(field = "name")
    @Length(min = 2, max = 8)
    private String name;

    @NotEmpty
    @Length(min = 4, max = 16)
    private String password;
    private Date joinDate;
}
