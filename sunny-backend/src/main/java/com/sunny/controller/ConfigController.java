package com.sunny.controller;

import com.sunny.common.Result;
import com.sunny.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/{key}")
    public Result<String> get(@PathVariable String key) {
        return Result.success(configService.getValueByKey(key));
    }

    @PostMapping
    public Result<Void> set(@RequestParam String key, @RequestParam String value) {
        configService.setValueByKey(key, value);
        return Result.success();
    }
}
