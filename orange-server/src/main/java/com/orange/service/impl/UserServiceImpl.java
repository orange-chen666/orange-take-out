package com.orange.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.orange.constant.JwtClaimsConstant;
import com.orange.dto.UserLoginDTO;
import com.orange.entity.User;
import com.orange.mapper.UserMapper;
import com.orange.properties.JwtProperties;
import com.orange.properties.WeChatProperties;
import com.orange.service.UserService;
import com.orange.utils.HttpClientUtil;
import com.orange.utils.JwtUtil;
import com.orange.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserLoginVo login(UserLoginDTO userLoginDTO) {
        Map<String, String> paramMap= new HashMap<>();
        paramMap.put("appid",weChatProperties.getAppid());
        paramMap.put("secret",weChatProperties.getSecret());
        paramMap.put("grant_type","authorization_code");
        paramMap.put("js_code",userLoginDTO.getCode());
        String json = HttpClientUtil.doPost(WX_LOGIN,paramMap);
        JSONObject jsonObject = JSONObject.parseObject(json);
        log.info("使用登录凭证校验接口后，前端传给后端的数据:{}",jsonObject);
        String openId = jsonObject.getString("openid");
        //如果没有注册就注册
        User user = userMapper.getOpenId(openId);
        if (user == null) {
           user = User.builder().openid(openId).createTime(LocalDateTime.now()).build();
            userMapper.add(user);//这里面mybatis获取了生成的主键id并传入user里面
            //具体的姓名啊，性别啥的咋搞
        }
        //生成token
        Map<String, Object> claims = new HashMap<>();//加啥？
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        claims.put(JwtClaimsConstant.EMP_ID,user.getName());
        String token = JwtUtil.creatJWT(jwtProperties.getAdminSecretKey(),claims,jwtProperties.getAdminTtl());
        UserLoginVo userLoginVo = UserLoginVo.builder()
                .id(user.getId())
                .token(token)
                .openid(openId)
                .build();
        return userLoginVo;


    }
}
