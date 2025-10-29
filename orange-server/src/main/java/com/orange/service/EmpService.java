package com.orange.service;

import com.orange.dto.EmpPageQueryDTO;
import com.orange.entity.Emp;
import org.springframework.stereotype.Service;

@Service
public interface EmpService {
    Emp page(EmpPageQueryDTO empPageQueryDTO);
}
