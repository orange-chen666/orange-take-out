package com.orange.aspect;

import com.orange.annotation.AutoFill;
import com.orange.context.BaseContext;
import com.orange.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.xml.crypto.dsig.SignatureMethod;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 公共字段自动填充
 * 只对insert 和 updat数据库操作生效
 * 搞太久了这部分，反射枚举aop
 */

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //execution 我省略了前后访问修饰符，还有异常，包名类名没有省略
    //返回值 方法名（参数）
    //* com.orange.mapper.*.*(..)切入表达式
    /**
     * 切入点
     */
    @Pointcut("execution(* com.orange.mapper.*.*(..)) && @annotation(com.orange.annotation.AutoFill)")
    public void AutoFillPointCut() {}

    @Before("AutoFillPointCut()")
    public void AutoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段自动填充...");
        //：转换为 MethodSignature 是为了获取方法的详细信息，
        // 特别是为了能够访问方法上的注解，这是通用 Signature 接口无法提供的功能。
//         Signature signature = joinPoint.getSignature();
//        SignatureMethod signatureMethod = (SignatureMethod) signature;
//        AutoFill autoFill  = signatureMethod.getClass().getAnnotation(AutoFill.class);
//         OperationType autoFill1  = autoFill.value();
        //SignatureMethod vs MethodSignature
        Signature signature = joinPoint.getSignature();
        MethodSignature Methodsignature = (MethodSignature) signature;
        AutoFill autoFill  = Methodsignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object entity = args[0];


//        getDeclaredMethod 是 Class 类的一个方法，用于获取当前类声明的指定方法，
//        包括 public、protected、default 和 private 方法，但它不包括继承的方法。
        LocalDateTime localDateTime = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();
        try {
            if (operationType == OperationType.INSERT) {
                Method setCreateTime = entity.getClass().getDeclaredMethod("getCreateTime",LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                setCreateTime.invoke(entity,localDateTime);
                setCreateUser.invoke(entity,id);
                setUpdateTime.invoke(entity,localDateTime);
                setUpdateUser.invoke(entity,id);
            }else {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                setUpdateTime.invoke(entity,localDateTime);
                setUpdateUser.invoke(entity,id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
