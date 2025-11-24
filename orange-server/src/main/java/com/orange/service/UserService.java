package com.orange.service;

import com.orange.dto.UserLoginDTO;
import com.orange.vo.UserLoginVo;

public interface UserService {

    UserLoginVo login(UserLoginDTO userLoginDTO);
}
