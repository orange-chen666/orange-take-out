package com.orange.service.impl;

import com.github.pagehelper.PageHelper;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.entity.Emp;
import com.orange.service.EmpService;

public class EmpServiceImpl implements EmpService {
    @Override
    public Emp page(EmpPageQueryDTO empPageQueryDTO) {
        PageHelper.startPage(empPageQueryDTO);
        //Pge这东西得看看；

        return null;
    }
}
