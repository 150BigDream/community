package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.community.model.User;
import com.example.community.mapper.UserMapper;
import com.example.community.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-03-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService;

    public void createOrUpdate(User user) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account_id",user.getAccountId());
        User dbUser=userMapper.selectOne(queryWrapper);
//      User dbUser=userMapper.selectByAccountId(user.getAccountId());
        if (dbUser==null){
            //数据库没有该用户，插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            user.setGmtModified(System.currentTimeMillis());
            dbUser.setGmtModified(user.getGmtModified());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setBio(user.getBio());
//          BeanUtils.copyProperties(user,dbUser);//dbUser属性值不为空的时候似乎是不能用的，会报错
            userMapper.updateById(dbUser);
        }
    }
}
