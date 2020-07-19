package com.it.community.mapper;

import com.it.community.dto.QuestionQueryDTO;
import com.it.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);

	Integer countBySearch(QuestionQueryDTO questionQueryDTO);

	List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

}