package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionServiceImpl questionService;

    /**
     * 问题详情功能
     * @param id
     * @param model
     * @return
     */

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Long id, Model model){
        QuestionDTO  questionDTO= questionService.selectById(id);

        model.addAttribute("question",questionDTO);
        return "question";
    }
}
