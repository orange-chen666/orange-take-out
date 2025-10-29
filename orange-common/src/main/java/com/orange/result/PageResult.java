package com.orange.result;

import lombok.Data;

@Data
public class PageResult {
    private String name;
    private Long page;
    private Long pageSize;
}
