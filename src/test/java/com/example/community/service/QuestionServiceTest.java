package com.example.community.service;

import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionServiceTest {
    @Autowired
    IQuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;
    @Test
    public void selectById(){
        User user = userMapper.selectById(6);
        System.out.println(user);
    }}

//    @Test
//    public void selectAll() {
//        List<QuestionDTO> questionDTOList = questionService.selectAll(1, 5);
//        for (QuestionDTO questionDTO : questionDTOList) {
//            System.out.println(questionDTO.getId());
//        }
//    }

