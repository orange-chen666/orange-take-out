package com.orange.service;

import com.orange.dto.EmpDTO;
import com.orange.dto.EmpLoginDTO;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.dto.EmpPwdDTO;
import com.orange.entity.Emp;
import com.orange.result.PageResult;
import com.orange.vo.EmpLoginVO;
import org.springframework.stereotype.Service;

//不用service注解
public interface EmpService {
    PageResult page(EmpPageQueryDTO empPageQueryDTO);

    void add(EmpDTO empDTO);

    void editPwd(EmpPwdDTO empPwdDTO);

    EmpLoginVO login(EmpLoginDTO empLoginDTO);
}
