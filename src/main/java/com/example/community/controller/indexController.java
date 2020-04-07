package com.example.community.controller;

import com.example.community.dto.PaginationDTO;
import com.example.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

    @Autowired
    QuestionServiceImpl questionService;

    /**
     * 列表所有问题页面
     */
    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {

        PaginationDTO pageInfo = questionService.selectAll(page, size);
        model.addAttribute("pageInfo", pageInfo);

        return "index";
    }
}
