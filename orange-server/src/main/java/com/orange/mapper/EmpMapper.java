package com.orange.mapper;

import com.github.pagehelper.Page;
import com.orange.annotation.AutoFill;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.entity.Emp;
import com.orange.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmpMapper {
    /**
     * 分页查询
     */

    Page<Emp> pageQuery(EmpPageQueryDTO empPageQueryDTO);

    /**
     * 添加员工
     */
    @Insert("insert into employee ( name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user)" +
            "values (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void add(Emp emp);

    /**
     * 登录
     */
    @Select("select * from employee where username = #{username}")
    //一开始将是"select * from employee where #{username}"少了username =
    Emp login(String username);

    @Update("update employee set status = #{status} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void starOrStop(Integer status, Integer id);
}

