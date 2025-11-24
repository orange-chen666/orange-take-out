package com.orange.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.orange.dto.SetmealDTO;
import com.orange.dto.SetmealPageQueryDTO;
import com.orange.entity.Setmeal;
import com.orange.mapper.SetmealMapper;
import com.orange.result.PageResult;
import com.orange.service.SetmealService;
import com.orange.vo.DishItemVO;
import com.orange.vo.DishVO;
import com.orange.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {

    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {

    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        return null;
    }

    @Override
    public void update(SetmealDTO setmealDTO) {

    }

    @Override
    public void startOrStop(Integer status, Long id) {

    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
