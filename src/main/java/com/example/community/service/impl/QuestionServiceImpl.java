package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.community.Exception.CustomizeErrorCode;
import com.example.community.Exception.CustomizeException;
import com.example.community.dto.PaginationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.mapper.QuestionMapper;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-03-13
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionServiceImpl questionService;

    /**
     * 列表所有问题
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO selectAll(Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount=questionService.count();

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

        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setList(questionDTOList);
        return paginationDTO;
    }

    /**
     * 根据id选择出我的问题
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO selectList(Long userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        QueryWrapper<Question> questionQueryWrapper=new QueryWrapper<>();
        questionQueryWrapper.eq("creator",userId);
        Integer totalCount=questionService.count(questionQueryWrapper);
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

        Integer offset = size * (page - 1);
        List<Question> questions=questionMapper.selectListByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());
            System.out.println(user.getId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setList(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO selectById(Long id) {
        Question question = questionMapper.selectById(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        //累加阅读数
        UpdateWrapper<Question> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("view_count",question.getViewCount()+1).setEntity(question);
        //像浏览数这种东西，要考虑并发，从数据库层面操作
        questionService.update(updateWrapper);

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

    /**
     * 根据正则标签查询到相近的问题
     * @param queryDTO
     * @return
     */
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (queryDTO.getTag()==null){
            return new ArrayList<>();
        }
        String[] tags= StringUtils.split(queryDTO.getTag(),",");
        String regexpTag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
