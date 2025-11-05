package com.orange.utils;

import com.orange.properties.JwtProperties;
import com.orange.result.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static String creatJWT(String SecretKey ,Map<String,Object> claims ,long ttlMillis) {
//        JwtProperties a = new JwtProperties();
//        a.getAdminSecretKey();
//        new JwtProperties()直接创建对象，绕过了Spring的IoC容器
        //报错了密钥长度过短JWT密钥长度不够。HS256算法要求密钥长度至少为256位（32字节）
        long expMills = System.currentTimeMillis() + ttlMillis;//毫秒的？
        Date date = new Date(expMills);
        return Jwts.builder().
                signWith(SignatureAlgorithm.HS256, SecretKey).
                addClaims(claims).
                setExpiration(date)
                .compact();
    }
    public static Claims parseJWT(String SecretKey,String token) {
        return Jwts.parserBuilder().
                setSigningKey(SecretKey).
                build().
                parseClaimsJws(token).////源码会抛异常的
                getBody();
    }
}
