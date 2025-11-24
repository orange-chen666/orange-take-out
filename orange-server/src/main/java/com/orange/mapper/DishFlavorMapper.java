package com.orange.mapper;

import com.orange.entity.DishFlavor;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getById(Long id);
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void delete(Long id);

    void insertBatch(List<DishFlavor> dishFlavors);
}
