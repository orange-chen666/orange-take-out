package com.orange.controller.admin;

import com.orange.dto.DishDTO;
import com.orange.dto.DishPageQueryDTO;
import com.orange.entity.Dish;
import com.orange.result.PageResult;
import com.orange.result.Result;
import com.orange.service.DishService;
import com.orange.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);

    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping()
    public Result upload(DishDTO dishDTO) {
        log.info("修改菜品:{}",dishDTO);
        dishService.upload(dishDTO);
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping()
    public Result add(DishDTO dishDTO) {
        log.info("添加菜品:{}",dishDTO);
        dishService.add(dishDTO);
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }
    /**
     * 根据id查询菜品
     */
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品{}",id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }
    /**
     * 菜品起售、停售
     */
    @PostMapping("status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("菜品状态{},id{}",status,id);
        dishService.starOrstop(status,id);
        return Result.success();
    }
    /**
     * 批量删除
     */
    @DeleteMapping()
    public Result deleteByIds(List<Long> ids) {
        log.info("批量删除{}",ids);
        dishService.deleteByIds(ids);
        cleanCache("dish_*");
        return Result.success();
    }
    /**
     * 根据分类id查询菜品
     */
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("分类id{}",categoryId);
        List<Dish> dish = dishService.list(categoryId);
        return Result.success(dish);
    }
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
