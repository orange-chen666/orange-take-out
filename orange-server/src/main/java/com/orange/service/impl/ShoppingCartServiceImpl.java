package com.orange.service.impl;

import com.orange.context.BaseContext;
import com.orange.dto.ShoppingCartDTO;
import com.orange.entity.Setmeal;
import com.orange.entity.ShoppingCart;
import com.orange.mapper.DishMapper;
import com.orange.mapper.SetmealMapper;
import com.orange.mapper.ShoppingCartMapper;
import com.orange.service.ShoppingCartService;
import com.orange.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();//这个不是当前线程的id吗？
        log.info("当前用户id{}",userId);
        shoppingCart.setUserId(userId);
         List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
         if (shoppingCartList != null&& !shoppingCartList.isEmpty()) {
            ShoppingCart sc = shoppingCartList.get(0);
            sc.setNumber(sc.getNumber() + 1);
            shoppingCartMapper.update(sc);//我这个update方法是全部更不只是更新一个number字段
         }else {
             Long dishId = shoppingCartDTO.getDishId();
             if (dishId != null) {
                 DishVO dishVO = dishMapper.getById(dishId);
                 shoppingCart.setName(dishVO.getName());
                 shoppingCart.setAmount(dishVO.getPrice());
                 shoppingCart.setImage(dishVO.getImage());
             }else {
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
             }
             shoppingCart.setNumber(1);
             shoppingCart.setCreateTime(LocalDateTime.now());
             shoppingCartMapper.add(shoppingCart);
         }

    }
    /**
     * 查看购物车
     */
    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder().
                userId(userId).
                build();
        return shoppingCartMapper.list(shoppingCart);
    }

    @Override
    public void delete(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        //设置查询条件，查询当前登录用户的购物车数据
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if(list != null && list.size() > 0){
            shoppingCart = list.get(0);

            Integer number = shoppingCart.getNumber();
            if(number == 1){
                //当前商品在购物车中的份数为1，直接删除当前记录
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }else {
                //当前商品在购物车中的份数不为1，修改份数即可
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }
        }
    }

    @Override
    public void cleanShoppingCart() {
//获取到当前微信用户的id
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }

}
