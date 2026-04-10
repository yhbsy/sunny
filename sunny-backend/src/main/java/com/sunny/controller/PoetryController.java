package com.sunny.controller;

import com.sunny.common.PageResult;
import com.sunny.common.Result;
import com.sunny.dto.PageDTO;
import com.sunny.dto.PoetryDTO;
import com.sunny.entity.Poetry;
import com.sunny.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/poetry")
public class PoetryController {

    @Autowired
    private PoetryService poetryService;

    @GetMapping("/list")
    public Result<List<Poetry>> list() {
        return Result.success(poetryService.listAll());
    }

    @GetMapping("/page")
    public Result<PageResult<Poetry>> page(PageDTO pageDTO) {
        return Result.success(poetryService.pageList(pageDTO));
    }

    @GetMapping("/{id}")
    public Result<Poetry> detail(@PathVariable Long id) {
        return Result.success(poetryService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@Valid @RequestBody PoetryDTO dto) {
        poetryService.addPoetry(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@Valid @RequestBody PoetryDTO dto) {
        poetryService.updatePoetry(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        poetryService.deletePoetry(id);
        return Result.success();
    }
}
