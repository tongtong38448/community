package com.it.community.service;

import com.it.community.dto.PageInfo;
import com.it.community.dto.QuestionDTO;
import com.it.community.mapper.QuestionMapper;
import com.it.community.mapper.UserMapper;
import com.it.community.model.Question;
import com.it.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public PageInfo findAll(Integer page, Integer size) {

        PageInfo pageInfo = new PageInfo();
        Integer totalCount = questionMapper.count();
        pageInfo.setPageInfo(totalCount,page,size);
        if(page<1){
            page=1;
        }
        if(page>pageInfo.getTotalPage()){
            page=pageInfo.getTotalPage();
        }



        Integer offset = size*(page-1);

        List<Question> questions = questionMapper.findAll(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question :
                questions) {
           User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
           //question取出来 给questionDTO set上
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        pageInfo.setQuestions(questionDTOList);


        return pageInfo;
    }
}
