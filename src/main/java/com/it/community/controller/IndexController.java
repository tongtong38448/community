package com.it.community.controller;

import com.it.community.dto.PageInfo;
import com.it.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search
                        ){
        PageInfo pageInfo = questionService.findAll(search,page, size);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("search", search);
        return "index";
    }
}
