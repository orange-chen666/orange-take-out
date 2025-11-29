package com.orange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DishFlavor implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    //菜品id
    private Long dishId;
    //口味名称
    private String name;
    //口味数据list
    private String value;
}
