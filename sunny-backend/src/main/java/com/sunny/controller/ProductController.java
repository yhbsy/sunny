package com.sunny.controller;

import com.sunny.common.PageResult;
import com.sunny.common.Result;
import com.sunny.dto.PageDTO;
import com.sunny.dto.ProductDTO;
import com.sunny.entity.Product;
import com.sunny.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public Result<List<Product>> list() {
        return Result.success(productService.listAll());
    }

    @GetMapping("/category/{categoryId}")
    public Result<List<Product>> listByCategory(@PathVariable Long categoryId) {
        return Result.success(productService.listByCategoryId(categoryId));
    }

    @GetMapping("/page")
    public Result<PageResult<Product>> page(PageDTO pageDTO) {
        return Result.success(productService.pageList(pageDTO));
    }

    @GetMapping("/{id}")
    public Result<Product> detail(@PathVariable Long id) {
        return Result.success(productService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@Valid @RequestBody ProductDTO dto) {
        productService.addProduct(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@Valid @RequestBody ProductDTO dto) {
        productService.updateProduct(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Result.success();
    }
}
