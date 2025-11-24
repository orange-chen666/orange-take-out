package com.orange.service;

import com.orange.dto.DishDTO;
import com.orange.dto.DishPageQueryDTO;
import com.orange.entity.Dish;
import com.orange.result.PageResult;
import com.orange.vo.DishVO;

import java.util.List;

public interface DishService {
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
    void upload(DishDTO dishDTO);

    void add(DishDTO dishDTO);

    DishVO getById(Long id);

    void starOrstop(Integer status, Long id);

    void deleteByIds(List<Long> ids);

    List<DishVO> listByCategoryId(Long categoryId);

    List<Dish> list(Long categoryId);
}
