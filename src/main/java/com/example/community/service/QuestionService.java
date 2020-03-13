package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.dto.PaginationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.model.Question;
import org.springframework.stereotype.Service;


/**
 * 组装层，同时用userMapper和questionMapper
 */
@Service
public interface QuestionService extends IService<Question> {
    void creatOrUpdate(Question question);
    QuestionDTO selectById(Long id);
    PaginationDTO selectAll(Integer page, Integer size);
    PaginationDTO selectList(Long userId, Integer page, Integer size);

}
