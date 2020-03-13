package com.example.community.mapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.community.model.User;
import com.example.community.service.impl.UserServiceImpl;
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

    @Test
    public void findByToken() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("token","40172791-b1be-48d2-85fa-d40d6a58b3d7");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user.getName());
    }

}