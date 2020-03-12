package com.example.community.service;

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
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDTO selectAll(Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount=questionMapper.count();

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = page < 1 ? 0 : size * (page - 1);
        List<Question> questions=questionMapper.selectList(offset,size);

       // List<Question> questions=questionMapper.selectAll();
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //PageInfo pageInfo = new PageInfo(questionDTOList);
        paginationDTO.setList(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO selectList(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount=questionMapper.countById(userId);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }


        paginationDTO.setPagination(totalPage,page);

        Integer offset = page < 1 ? 0 : size * (page - 1);
        List<Question> questions=questionMapper.selectListByUserId(userId,offset,size);
        // List<Question> questions=questionMapper.selectAll();
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            System.out.println(user.getId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //PageInfo pageInfo = new PageInfo(questionDTOList);
        paginationDTO.setList(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO selectById(Integer id) {
        Question question = questionMapper.selectById(id);
        User user = userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }


    public void creatOrUpdate(Question question) {
        Question dbQuestion = questionMapper.selectById(question.getId());
        if (dbQuestion==null){
            //数据库没有，插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            //更新
            dbQuestion.setCreator(question.getCreator());
            dbQuestion.setTag(question.getTag());
            dbQuestion.setTitle(question.getTitle());
            dbQuestion.setDescription(question.getDescription());
            dbQuestion.setGmtModified(System.currentTimeMillis());
            questionMapper.updateById(dbQuestion);
        }
    }
}
