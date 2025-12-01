package com.orange.service;

import com.orange.dto.OrdersSubmitDTO;
import com.orange.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
