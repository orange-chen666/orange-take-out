package com.orange.controller.user;
import com.orange.result.Result;
import com.orange.service.DishService;
import com.orange.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/list")//来一遍手动的
    public Result<List<DishVO>> list(Long categoryId) {
        String key = "dish::" + categoryId;
        log.info("菜品查询根据分类id{}",categoryId);
        List<DishVO> list = (List<DishVO>)redisTemplate.opsForValue().get(key);
        if (list != null && list.isEmpty()) {
            return Result.success(list);
        }
        List<DishVO> dishList = dishService.listByCategoryId(categoryId);
        redisTemplate.opsForValue().set(key,dishList);
        return Result.success(dishList);
    }
}
