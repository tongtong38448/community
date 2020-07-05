package com.it.community.controller;

import com.it.community.cache.TagCache;
import com.it.community.dto.QuestionDTO;
import com.it.community.dto.TagDTO;
import com.it.community.model.Question;
import com.it.community.model.User;
import com.it.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish( Model model){
        List<TagDTO> tagDTOS = TagCache.get();
        model.addAttribute("tags", tagDTOS);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false)String title,
            @RequestParam(value ="description",required = false)String description,
            @RequestParam(value ="tag",required = false)String tag,
            @RequestParam(value ="id",required = false) Long id,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());
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
        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error","输入非法标签："+invalid);
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());

        question.setId(id);
        questionService.createOrUpdate(question);
       return "redirect:/";
    }
}
