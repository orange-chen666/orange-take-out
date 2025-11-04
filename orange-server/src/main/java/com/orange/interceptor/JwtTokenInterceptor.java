package com.orange.interceptor;

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
import org.springframework.web.servlet.ModelAndView;

/**
 * jwt令牌校验的拦截器
 */
@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ////判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);//解析token
            Long empId = Long.valueOf(claims.get("empId").toString());
            log.info("当前员工id：", empId);
            //因为在后续的业务处理中，经常需要知道当前操作的用户是谁。
            BaseContext.setCurrentId(empId);
            return true;

// claims（载荷）
// Map.get() 方法返回的是 Object 类型，即使存储的是数字，也会被当作 Object 返回
//            log.info("当前员工id：", empId);
//            解析 JWT 令牌，获取其中的员工 ID
//            将员工 ID 存储到 BaseContext 的 ThreadLocal 中
            //为什么需要这样做？
            //因为在后续的业务处理中，经常需要知道当前操作的用户是谁。通过 ThreadLocal，
            // 可以在同一个线程（即同一个请求）的任何地方通过 BaseContext.getCurrentId() 获取到当前用户ID，
            // 而不需要在每个方法参数中都传递用户ID。
            //ThreadLocal 的优势
            //使用 ThreadLocal 的好处是：
            //数据隔离：每个线程都有自己的副本，线程之间互不干扰
            //方便访问：在同一线程的任何地方都可以方便地获取到存储的数据
            //避免参数传递：不需要在方法间传递用户ID参数
            //当请求处理完成后，通常会在拦截器或过滤器中调用 BaseContext.removeCurrentId()
            // 来清理 ThreadLocal 中的数据，避免内存泄漏。
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }
}
//在 Spring MVC 中，handler 是指处理器，它代表了将要被调用来处理当前 HTTP 请求的对象。
//在拦截器的 preHandle 方法中，第三个参数 Object handler 就是这个处理器。它可以是以下几种类型：
//HandlerMethod：最常见的类型，代表一个 Controller 中的方法
//ResourceHttpRequestHandler：用于处理静态资源请求
//其他类型的处理器
//instanceof 是 Java 中的一个关键字，
//用于检查对象是否属于某个特定类型（包括其子类型）。它是一个二元操作符，返回一个布尔值（true 或 false）。