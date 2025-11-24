package com.orange.controller.user;

import com.orange.dto.UserLoginDTO;
import com.orange.result.Result;
import com.orange.service.UserService;
import com.orange.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {
    /**
     * 微信登录
     */
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result<UserLoginVo> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("获取微信授权码:{}",userLoginDTO);
        UserLoginVo userLoginVo = userService.login(userLoginDTO);
        return Result.success(userLoginVo);
    }
}
