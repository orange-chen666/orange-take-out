package com.orange.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpLoginDTO implements Serializable {
    private String password;
    private String username;
}
