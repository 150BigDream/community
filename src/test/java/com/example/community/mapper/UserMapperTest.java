package com.example.community.mapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.community.model.User;
import com.example.community.service.impl.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService;
    @Test
    public void select(){
        List<User> users =userService.list();
        for (User user:users
             ) {
            System.out.println(user.getId()+user.getName());
        }
    }
    @Test
    public void insert(){
        User user = new User();
        user.setAccountId("2313");
        userMapper.insert(user);
    }


}