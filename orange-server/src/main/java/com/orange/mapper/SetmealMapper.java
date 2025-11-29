package com.orange.mapper;

import com.github.pagehelper.Page;
import com.orange.dto.SetmealPageQueryDTO;
import com.orange.dto.ShoppingCartDTO;
import com.orange.entity.Setmeal;
import com.orange.vo.DishItemVO;
import com.orange.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    List<Setmeal> list(Setmeal setmeal);
    /**
     * 根据套餐id查询菜品选项
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    @Select("select * from setmeal where id = #{setmealId}")
    Setmeal getById(Long setmealId);
    void delete(ShoppingCartDTO shoppingCartDTO);
}
