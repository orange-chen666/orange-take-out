package com.orange.controller.user;

import com.orange.entity.Category;
import com.orange.result.Result;
import com.orange.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("分类查询:{}",type);
         List<Category> categoryList = categoryService.list(type);
        return Result.success(categoryList);
    }
}
