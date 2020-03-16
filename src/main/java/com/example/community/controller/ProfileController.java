package com.example.community.controller;

import com.example.community.dto.PaginationDTO;
import com.example.community.model.User;
import com.example.community.service.impl.NotificationServiceImpl;
import com.example.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
//TODO 所有的active都没有起作用
@Controller
public class ProfileController {
    @Autowired
    QuestionServiceImpl questionService;
    @Autowired
    NotificationServiceImpl notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action")String action, Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request
    ){
        User user = (User) request.getSession().getAttribute("user");
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
            PaginationDTO pageInfo=questionService.selectList(user.getId(),page,size);
            model.addAttribute("pageInfo",pageInfo);
        }
        else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            PaginationDTO pageInfo=notificationService.selectList(user.getId(),page,size);
            model.addAttribute("pageInfo",pageInfo);
        }

        return "profile";
    }
}
