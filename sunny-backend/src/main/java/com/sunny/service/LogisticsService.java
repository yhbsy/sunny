package com.sunny.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunny.entity.Logistics;

import java.util.List;

public interface LogisticsService extends IService<Logistics> {
    List<Logistics> listAll();
}
