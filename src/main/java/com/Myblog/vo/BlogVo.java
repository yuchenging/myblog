package com.Myblog.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogVo {

    private String groupName;//分组名称
    private long noteCount;//博客数量
    private Integer typeId;//类型ID
    private String typeName;//博客类型

}
