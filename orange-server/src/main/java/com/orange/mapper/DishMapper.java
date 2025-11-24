package com.orange.mapper;

import com.github.pagehelper.Page;
import com.orange.annotation.AutoFill;
import com.orange.dto.DishDTO;
import com.orange.dto.DishPageQueryDTO;
import com.orange.entity.Dish;
import com.orange.enumeration.OperationType;
import com.orange.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //分页
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //添加
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into dish(name, category_id, price, image, description,status)" +
            "values (#{name},#{categoryId},#{price},#{image},#{description},#{status})")
    void add(Dish dish);

    //编辑
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    @Select("select d.*, c.name as category_name from dish d join category c on d.category_id = c.id where d.id = #{id}")
    DishVO getById(Long id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteById(List<Long> ids);
    @Select("select id, name, category_id, price, image, description, status,  update_time, update_user from dish where category_id = #{categoryId}")
    Dish listByCategoryId(Long categoryId);

    List<Dish> list(Dish dish);
}
