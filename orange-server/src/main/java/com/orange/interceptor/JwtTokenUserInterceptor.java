package com.orange.interceptor;

import com.orange.constant.JwtClaimsConstant;
import com.orange.context.BaseContext;
import com.orange.properties.JwtProperties;
import com.orange.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt客户端令牌校验的拦截器
 */
@Slf4j
@Component
public class JwtTokenUserInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ////判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader(jwtProperties.getUserTokenName());
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);//解析token，
            // 如果没有提供 token，或者 token 无效，下面这行代码会抛出异常，被捕获然后执行response.setStatus(401);完全没有问题
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id：{}", userId);
            //因为在后续的业务处理中，经常需要知道当前操作的用户是谁。
            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception ex) {
            log.info("jwt校验2:{}，请求失败", token);
            response.setStatus(401);
            return false;
        }
    }
}
