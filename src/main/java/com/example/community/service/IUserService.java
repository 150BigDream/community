package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends IService<User> {
    void createOrUpdate(User user);
}
