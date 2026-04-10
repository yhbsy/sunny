package com.sunny.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunny.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> listAll();
}
