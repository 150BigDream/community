package com.example.community.controller;

import com.example.community.dto.PaginationDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;

    /**
     * //TODO 其实还蛮疑惑的，每次登陆的话，如果有GitHubuser，不管是不是同一个，都会重新生成一个随机token，
     * 这样子的话，重启或者什么，不还是会重新生成一个token吗？怎么识别在数据库有，是你呢？
     * cookie是存在浏览器的。好像明白了，key-value这种形式，key不可以同名，value会被更新掉。但是也会更新的啊，我看token的值没有变
     * 不更新的原因是因为是insert语句而不是update语句吗？有些属性相同的，它就不会再插入了
     *
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