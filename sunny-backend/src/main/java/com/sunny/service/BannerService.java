package com.sunny.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunny.entity.Banner;

import java.util.List;

public interface BannerService extends IService<Banner> {
    List<Banner> listAll();
}
