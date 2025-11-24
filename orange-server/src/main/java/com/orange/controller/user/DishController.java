package com.orange.controller.user;
import com.orange.entity.Dish;
import com.orange.result.Result;
import com.orange.service.DishService;
import com.orange.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("菜品查询根据分类id{}",categoryId);
        List<DishVO> dishList = dishService.listByCategoryId(categoryId);
        return Result.success(dishList);
    }
}
