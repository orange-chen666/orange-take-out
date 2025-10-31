package com.orange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class Emp {
    //private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String sex;
    private String idNumber;
    private Integer status;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;

}
//serialVersionUID是Java序列化机制中的一个标识符，用于确保序列化和反序列化过程中类版本的一致性。
// 当对象被序列化后，如果类的结构发生变化（如添加或删除字段），
// 可以通过比较serialVersionUID来判断是否可以安全地反序列化。
// 设置为1L表示这是该类的第一个版本
