package com.sunny.controller;

import com.sunny.common.PageResult;
import com.sunny.common.Result;
import com.sunny.dto.LoginDTO;
import com.sunny.dto.OrderDTO;
import com.sunny.entity.Order;
import com.sunny.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result<String> create(@Valid @RequestBody OrderDTO dto, @RequestHeader(value = "userId", required = false) Long userId) {
        String orderNo = orderService.createOrder(dto, userId);
        return Result.success(orderNo);
    }

    @GetMapping("/list")
    public Result<PageResult<Order>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestHeader(value = "userId", required = false) Long userId) {
        return Result.success(orderService.pageList(pageNum, pageSize, userId));
    }

    @GetMapping("/{id}")
    public Result<Order> detail(@PathVariable Long id, @RequestHeader(value = "userId", required = false) Long userId) {
        return Result.success(orderService.getDetail(id, userId));
    }
}
