package com.sunny.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunny.common.PageResult;
import com.sunny.dto.PageDTO;
import com.sunny.dto.PoetryDTO;
import com.sunny.entity.Poetry;

import java.util.List;

public interface PoetryService extends IService<Poetry> {
    List<Poetry> listAll();

    PageResult<Poetry> pageList(PageDTO pageDTO);

    void addPoetry(PoetryDTO dto);

    void updatePoetry(PoetryDTO dto);

    void deletePoetry(Long id);
}
