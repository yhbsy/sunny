package com.sunny.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunny.dto.LoginDTO;
import com.sunny.entity.Admin;

public interface AdminService extends IService<Admin> {
    String login(LoginDTO dto);

    Admin getByUsername(String username);
}
