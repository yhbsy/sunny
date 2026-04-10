package com.sunny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunny.entity.Logistics;
import com.sunny.mapper.LogisticsMapper;
import com.sunny.service.LogisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Override
    public List<Logistics> listAll() {
        LambdaQueryWrapper<Logistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Logistics::getStatus, 1)
                .orderByDesc(Logistics::getCreateTime);
        return list(wrapper);
    }
}
