package com.example.community.controller;

import com.example.community.dto.PageInfo;
import com.example.community.mapper.QuestionMapper;
import com.example.community.model.Question;
import com.example.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageInfoTestController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionServiceImpl questionService;
    @GetMapping(("/test"))
    public String test(Model model,
                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
    ){
        PageInfo<Question> pageInfo = new PageInfo<>();
        int offset=(pageNum-1)*pageSize;
        List<Question> questions = questionMapper.list(offset,pageNum);
        int count=questionService.count();
        pageInfo.setPageInfo(pageNum,pageSize,count);
        pageInfo.setList(questions);
        model.addAttribute("pageInfo",pageInfo);
        return "test";
    }
}
