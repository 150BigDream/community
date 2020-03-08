package com.example.community.mapper;

import com.example.community.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionMapperTest {

    @Autowired
    QuestionMapper questionMapper;
    @Test
    public void insert(){
        Question question=new Question();
        question.setTitle("title");
        question.setDescription("description");
        question.setTag("tag");
        question.setCreator(2);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);
    }
}