package com.example.community.service.impl;

import com.example.community.mapper.CommentMapper;
import com.example.community.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceImplTest {
    @Autowired
    CommentMapper commentMapper;
    @Test
    public void insert() {
        Comment comment=new Comment();
        comment.setId(1L);
        comment.setParentId(202L);
        comment.setCommentator(5L);
        comment.setType(1);
        commentMapper.insert(comment);


    }
}