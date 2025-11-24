package com.orange.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class User {
    private Long id;
    //微信用户唯一标识
    private String openid;
    //姓名
    private String name;
    //手机号手机号获取要企业版小程序
    private String phone;
    //性别 0 女 1 男
    private String sex;
    //身份证号
    private String idNumber;
    //头像
    private String avatar;
    //注册时间
    private LocalDateTime createTime;
}
