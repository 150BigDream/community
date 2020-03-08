package com.example.community.mapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.community.model.User;
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
    @Test
    public void select(){
        List<User> users =userMapper.selectAll();
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
    }
}