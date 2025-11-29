package com.orange.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    private Long id;
    //类型: 1菜品分类 2套餐分类
    private Integer type;
    //分类名称
    private String name;
    //顺序
    private Integer sort;
}
