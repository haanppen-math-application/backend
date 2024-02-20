package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.dto.QuestionDetails;
import com.hanpyeon.academyapi.board.dto.QuestionPreview;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {
    private final QuestionRepository questionRepository;
    private final QuestionRelatedMemberProvider questionRelatedMemberProvider;
    private final ImageService imageService;
    private final BoardMapper boardMapper;

    public void addQuestion(@Validated final QuestionRegisterDto questionDto) {
        QuestionRelatedMember questionMember = questionRelatedMemberProvider.getQuestionRelatedMember(questionDto);
        List<Image> imageSources = imageService.saveImage(questionDto.images());
        questionRepository.save(boardMapper.createEntity(
                questionDto,
                questionMember.requestMember(),
                questionMember.targetMember(),
                imageSources)
        );
    }
    public QuestionDetails getSingleQuestionDetails(final Long questionId) {
        return questionRepository.findById(questionId)
                .map(boardMapper::createQuestionDetails)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
    public Slice<QuestionPreview> loadLimitedQuestions(final Pageable pageable) {
        return questionRepository.findBy(pageable)
                .map(boardMapper::createQuestionPreview);
    }
}
