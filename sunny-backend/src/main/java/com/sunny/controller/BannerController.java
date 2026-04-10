package com.sunny.controller;

import com.sunny.common.Result;
import com.sunny.entity.Banner;
import com.sunny.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/list")
    public Result<List<Banner>> list() {
        return Result.success(bannerService.listAll());
    }
}
