package com.orange.dto;

import lombok.Data;

@Data
public class EmpPageQueryDTO {
    private String name;
    private Long page;
    private Long pageSize;
}
