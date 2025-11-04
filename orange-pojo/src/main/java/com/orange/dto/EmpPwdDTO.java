package com.orange.dto;

import lombok.Data;

@Data
public class EmpPwdDTO {
    Integer empId;
    String newPassword;
    String oldPassword;
}
