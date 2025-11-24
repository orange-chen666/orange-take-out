package com.orange.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.orange.constant.MessageConstant;
import com.orange.constant.SetmealConstant;
import com.orange.context.StatusConstant;
import com.orange.dto.DishDTO;
import com.orange.dto.DishPageQueryDTO;
import com.orange.entity.Dish;
import com.orange.entity.DishFlavor;
import com.orange.exception.DeletionNotAllowedException;
import com.orange.mapper.DishFlavorMapper;
import com.orange.mapper.DishMapper;
import com.orange.result.PageResult;
import com.orange.service.DishService;
import com.orange.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void upload(DishDTO dishDTO) {
        Dish dish = new Dish();
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();//这里边的菜品口味是典型的一对多，菜品口味的重要值也不高
        BeanUtils.copyProperties(dishDTO,dish);
        log.info("此时dish{}",dish);
        dishMapper.update(dish);
        dishFlavorMapper.delete(dishDTO.getId());
        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            dishFlavors.forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));
        }
        dishFlavorMapper.insertBatch(dishFlavors);

    }

    @Override
    public void add(DishDTO dishDTO) {
        Dish dish = new Dish();
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        BeanUtils.copyProperties(dishDTO,dish);
        Long dishId = dish.getId();
        dishMapper.add(dish);
        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            //因为前端传进来的只有name 和valve
            dishFlavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
        }
        dishFlavorMapper.insertBatch(dishFlavors);
    }

    @Override
    public DishVO getById(Long id) {
       DishVO dishVO  = dishMapper.getById(id);
       log.info("此时dishVO的值1：{}",dishVO);
       List<DishFlavor> flavors = dishFlavorMapper.getById(id);
       dishVO.setFlavors(flavors);
        log.info("此时dishVO的值2：{}",dishVO);
       return dishVO;
    }

    @Override
    public void starOrstop(Integer status, Long id) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.update(dish);
//        if (status.equals(SetmealConstant.DISABLE)) {
//
//        }
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        //判断当前菜品是否能够删除---是否存在起售中的菜品？，直接抛异常中断操作
        //这种方法不错，还有一种是过滤掉不符合要求的ID，然后只对符合条件的ID执行删除操作，有点麻烦哈
        for (Long id: ids) {
             DishVO dishVO = dishMapper.getById(id);
            if (dishVO.getStatus().equals(SetmealConstant.ENABLE)) {
                throw new  DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否能够删除---是否被套餐关联了？ 还没搞套餐
        dishMapper.deleteById(ids);//减少数据库连接压力,减少SQL解析和编译开销
        // 使用的单条SQL批量删除方式是正确的选择，效率高且代码简洁。
        for (Long id : ids) {
            dishFlavorMapper.delete(id);//这个就复用之前的方法了
        }
    }

    /**
     * 根据分类id查询
     * @param categoryId
     * @return
     */
    @Override
    public List<DishVO> listByCategoryId(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        List<Dish> dishList = dishMapper.list(dish);
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish d : dishList) {
           List<DishFlavor> dishFlavorList = dishFlavorMapper.getById(d.getId());
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);
            dishVO.setFlavors(dishFlavorList);
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }

    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .build();
        List<Dish> dishList = dishMapper.list(dish);
        return dishList;
    }
}
