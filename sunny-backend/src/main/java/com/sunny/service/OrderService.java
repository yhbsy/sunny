package com.sunny.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunny.common.PageResult;
import com.sunny.dto.OrderDTO;
import com.sunny.entity.Order;

public interface OrderService extends IService<Order> {
    String createOrder(OrderDTO dto, Long userId);

    PageResult<Order> pageList(Integer pageNum, Integer pageSize, Long userId);

    Order getDetail(Long id, Long userId);
}
