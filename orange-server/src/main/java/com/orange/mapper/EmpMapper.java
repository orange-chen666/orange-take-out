package com.orange.mapper;

import com.orange.dto.EmpPageQueryDTO;
import com.orange.entity.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmpMapper {
    @Select("select * from orange_take_out.employee")
    Emp page(EmpPageQueryDTO empPageQueryDTO);
}
