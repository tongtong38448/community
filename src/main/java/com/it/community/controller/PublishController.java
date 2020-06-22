package com.it.community.controller;

import com.it.community.mapper.QuestionMapper;
import com.it.community.mapper.UserMapper;
import com.it.community.model.Question;
import com.it.community.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || "".equals(title)){
            model.addAttribute("error","请输入标题");
            return "publish";
        }
        if(description==null || "".equals(description)){
            model.addAttribute("error","请输入内容");
            return "publish";
        }
        if(tag==null || "".equals(tag)){
            model.addAttribute("error","请输入标签");
            return "publish";
        }


        Question question = new Question();
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);

        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            question.setCreator(user.getId());
        }else {
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
       return "redirect:/";
    }
}
