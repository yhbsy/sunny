package com.sunny.controller;

import com.sunny.common.Result;
import com.sunny.entity.Logistics;
import com.sunny.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @GetMapping("/list")
    public Result<List<Logistics>> list() {
        return Result.success(logisticsService.listAll());
    }
}
