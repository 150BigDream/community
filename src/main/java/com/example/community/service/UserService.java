package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    void createOrUpdate(User user);
}
