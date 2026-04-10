package com.sunny.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunny.common.PageResult;
import com.sunny.dto.OrderDTO;
import com.sunny.entity.Order;
import com.sunny.entity.OrderItem;
import com.sunny.entity.Product;
import com.sunny.mapper.OrderItemMapper;
import com.sunny.mapper.OrderMapper;
import com.sunny.mapper.ProductMapper;
import com.sunny.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public String createOrder(OrderDTO dto, Long userId) {
        String orderNo = IdUtil.getSnowflakeNextIdStr();
        
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setStatus(0);
        order.setReceiverName(dto.getReceiverName());
        order.setReceiverPhone(dto.getReceiverPhone());
        order.setReceiverAddress(dto.getReceiverAddress());
        order.setRemark(dto.getRemark());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderDTO.OrderItemDTO item : dto.getItems()) {
            Product product = productMapper.selectById(item.getProductId());
            if (product == null) {
                throw new RuntimeException("商品不存在: " + item.getProductId());
            }
            
            BigDecimal itemAmount = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemAmount);
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getImage());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setAmount(itemAmount);
        }
        
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        save(order);
        
        for (OrderDTO.OrderItemDTO item : dto.getItems()) {
            Product product = productMapper.selectById(item.getProductId());
            BigDecimal itemAmount = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getImage());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setAmount(itemAmount);
            orderItemMapper.insert(orderItem);
        }
        
        return orderNo;
    }

    @Override
    public PageResult<Order> pageList(Integer pageNum, Integer pageSize, Long userId) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            wrapper.eq(Order::getUserId, userId);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        Page<Order> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getPages(), result.getCurrent(), result.getSize());
    }

    @Override
    public Order getDetail(Long id, Long userId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getId, id);
        if (userId != null) {
            wrapper.eq(Order::getUserId, userId);
        }
        Order order = getOne(wrapper);
        if (order != null) {
            LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
            itemWrapper.eq(OrderItem::getOrderId, order.getId());
            List<OrderItem> items = orderItemMapper.selectList(itemWrapper);
        }
        return order;
    }
}
