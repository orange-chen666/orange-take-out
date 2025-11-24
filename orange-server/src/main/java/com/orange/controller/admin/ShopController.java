package com.orange.controller.admin;

import com.orange.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/shop")
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺的营业状态
     * @param status
     * @return
     */
    @PutMapping("{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("收到店铺状态信息{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }
    /**
     * 获取店铺的营业状态
     */
    @GetMapping("status")
    public Result<Integer> getStatus() {
        log.info("获取店铺状态信息");
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("店铺状态信息{}",status);
        return Result.success(status);
    }
}
