package com.orange.mapper;

import com.orange.annotation.AutoFill;
import com.orange.entity.User;
import com.orange.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid = #{openId}")
    User getOpenId(String openId);
    void add(User user);
}
