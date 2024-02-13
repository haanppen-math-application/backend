package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.mapper.QuestionMapper;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {
    private final QuestionRepository questionRepository;
    private final QuestionMemberProvider questionMemberProvider;
    private final ImageService imageService;
    private final QuestionMapper questionMapper;

    public void addQuestion(@Validated final QuestionRegisterDto questionDto) {
        QuestionRelatedMember questionMember = questionMemberProvider.getQuestionRelatedMember(questionDto);
        List<Image> imageSources = imageService.saveImage(questionDto.images());
        questionRepository.save(questionMapper.createEntity(
                questionDto,
                questionMember.requestMember(),
                questionMember.targetMember(),
                imageSources)
        );
    }
}
