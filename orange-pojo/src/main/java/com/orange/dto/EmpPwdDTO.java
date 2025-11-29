package com.orange.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpPwdDTO implements Serializable {
    Integer empId;
    String newPassword;
    String oldPassword;
}
