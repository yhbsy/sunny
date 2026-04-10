package com.sunny.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunny.common.PageResult;
import com.sunny.dto.PageDTO;
import com.sunny.dto.PoetryDTO;
import com.sunny.entity.Poetry;
import com.sunny.mapper.PoetryMapper;
import com.sunny.service.PoetryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PoetryServiceImpl extends ServiceImpl<PoetryMapper, Poetry> implements PoetryService {

    @Override
    public List<Poetry> listAll() {
        LambdaQueryWrapper<Poetry> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Poetry::getStatus, 1)
                .orderByDesc(Poetry::getSort)
                .orderByDesc(Poetry::getCreateTime);
        return list(wrapper);
    }

    @Override
    public PageResult<Poetry> pageList(PageDTO pageDTO) {
        Page<Poetry> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        LambdaQueryWrapper<Poetry> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(pageDTO.getKeyword())) {
            wrapper.like(Poetry::getTitle, pageDTO.getKeyword())
                    .or()
                    .like(Poetry::getContent, pageDTO.getKeyword());
        }
        if (pageDTO.getStatus() != null) {
            wrapper.eq(Poetry::getStatus, pageDTO.getStatus());
        }
        wrapper.orderByDesc(Poetry::getSort).orderByDesc(Poetry::getCreateTime);
        Page<Poetry> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getPages(), result.getCurrent(), result.getSize());
    }

    @Override
    public void addPoetry(PoetryDTO dto) {
        Poetry poetry = new Poetry();
        BeanUtil.copyProperties(dto, poetry);
        save(poetry);
    }

    @Override
    public void updatePoetry(PoetryDTO dto) {
        Poetry poetry = new Poetry();
        BeanUtil.copyProperties(dto, poetry);
        updateById(poetry);
    }

    @Override
    public void deletePoetry(Long id) {
        removeById(id);
    }
}
