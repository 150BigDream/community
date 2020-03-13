package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.dto.PaginationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 组装层，同时用userMapper和questionMapper
 */
@Service
public interface IQuestionService extends IService<Question> {
    void creatOrUpdate(Question question);
    QuestionDTO selectById(Integer id);
    PaginationDTO selectAll(Integer page, Integer size);
    PaginationDTO selectList(Integer userId, Integer page, Integer size);

}
