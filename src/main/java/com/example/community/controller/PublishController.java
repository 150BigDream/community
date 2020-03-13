package com.example.community.controller;

import com.example.community.mapper.QuestionMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionServiceImpl questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id, Model model){
        Question question = questionMapper.selectById(id);
        model.addAttribute("question",question);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            @RequestParam(value = "id",required = false)Integer id,
            HttpServletRequest request,
            Model model
    ){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);
        question.setCreator(user.getId());
        questionService.creatOrUpdate(question);
//        questionMapper.insert(question);
        return "redirect:/";
    }
}
