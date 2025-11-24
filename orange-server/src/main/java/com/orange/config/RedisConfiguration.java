package com.orange.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration//标识该类为配置类，告诉 Spring 这个类包含 Bean 定义
/**
 * Spring 启动时扫描所有 @Configuration 类
 * 找到 RedisConfiguration 类
 * 执行 redisTemplate() 方法（因为有 @Bean 注解）
 * 将返回的 RedisTemplate 对象注册到 Spring 容器中
 * 其他类通过 @Autowired 就可以注入使用了
 * 自动配置是核心：Spring Boot 根据依赖自动配置组件，您只需要通过 @Configuration 类进行微调即可。
 * 整个流程体现了 "约定优于配置" 的设计理念！
 */
public class RedisConfiguration {
    @Bean//加了个bean咋可以用RedisConnectionFactory
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
//    Spring Boot 的自动配置机制
//    自动配置生效：因为您引入了 spring-boot-starter-data-redis ，Spring Boot 会自动配置 Redis
//    默认 RedisTemplate：Spring Boot 会创建一个默认的 RedisTemplate Bean
//    但缺少关键配置：默认配置没有设置序列化器
    //为什么需要这个RedisConnectionFactory参数？
    //因为 RedisTemplate 需要知道如何连接到 Redis：
    //Spring Boot 的便利性
    //由于您引入了 spring-boot-starter-data-redis ，Spring Boot 已经：
    //
    //读取了 application.yml 中的 Redis 配置
    //自动创建了 RedisConnectionFactory Bean
    //您只需要在配置方法中接收并使用它
}
