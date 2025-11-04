package com.orange.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpLoginVO {
    private Long id;
    private String name;
    private String userName;
    private String token;
}
