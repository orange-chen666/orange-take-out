package com.orange.mapper;

import com.orange.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    @Insert("insert into address_book" +
            "        (user_id, consignee, phone, sex, province_code, province_name, city_code, city_name, district_code," +
            "         district_name, detail, label, is_default)" +
            "        values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}," +
            "                #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void add(AddressBook addressBook);
    @Select("select * from address_book where user_id = #{currentId}")
    List<AddressBook> listByUserId(Long currentId);
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    void update(AddressBook addressBook);
    @Update("update address_book set is_default = 0 where user_id = #{currentId}")
    void setDefault(Long currentId);
    @Select("select * from address_book where user_id = #{userId} and is_default = #{isDefault}")
    AddressBook list();
    @Select("select * from address_book where is_default = #{isDefault} and user_id = #{userId}")
    List<AddressBook> getDefault(AddressBook addressBook);
}
