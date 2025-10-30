package com.orange.mapper;

import com.github.pagehelper.Page;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.entity.Emp;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EmpMapper {
    Page<Emp> pageQuery(EmpPageQueryDTO empPageQueryDTO);
}
