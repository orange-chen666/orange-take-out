package com.orange.service;

import com.orange.dto.ShoppingCartDTO;
import com.orange.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    void add(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();

    void delete(ShoppingCartDTO shoppingCartDTO);

    void cleanShoppingCart();
}
