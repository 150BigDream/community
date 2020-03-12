package com.example.community.service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public void createOrUpadat(User user) {
        User dbUser=userMapper.selectByAccountId(user.getAccountId());
        if (dbUser==null){
            //数据库没有该用户，插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            user.setGmtModified(System.currentTimeMillis());
//            dbUser.setGmtModified(user.getGmtModified());
//            dbUser.setName(user.getName());
//            dbUser.setToken(user.getToken());
//            dbUser.setAvatarUrl(user.getAvatarUrl());
//            dbUser.setBio(user.getBio());
            BeanUtils.copyProperties(user,dbUser);
            userMapper.updateById(dbUser);
        }
    }
}
