package com.orange.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.entity.Emp;
import com.orange.mapper.EmpMapper;
import com.orange.result.PageResult;
import com.orange.result.Result;
import com.orange.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public PageResult page(EmpPageQueryDTO empPageQueryDTO) {
//        Page a = PageHelper.startPage(empPageQueryDTO);//不是得传页码，还有每页展示数量
//        System.out.println(a);
        //Pge这东西得看看；
        PageHelper.startPage(empPageQueryDTO.getPage(),empPageQueryDTO.getPageSize());
        Page<Emp> page = empMapper.pageQuery(empPageQueryDTO);
        Long total = page.getTotal();
        List<Emp> result = page.getResult();
        return new PageResult(total,result);
    }
}
