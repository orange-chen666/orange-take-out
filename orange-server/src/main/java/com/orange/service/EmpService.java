package com.orange.service;

import com.orange.dto.EmpDTO;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.entity.Emp;
import com.orange.result.PageResult;
import org.springframework.stereotype.Service;

//不用service注解
public interface EmpService {
    PageResult page(EmpPageQueryDTO empPageQueryDTO);

    void add(EmpDTO empDTO);
}
