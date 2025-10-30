package com.orange.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpPageQueryDTO implements Serializable {
    private String name;
    private int page;
    private int pageSize;
}
