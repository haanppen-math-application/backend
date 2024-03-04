package com.hanpyeon.academyapi.board.service.question;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import com.hanpyeon.academyapi.board.service.question.register.QuestionRelatedMember;
import com.hanpyeon.academyapi.board.service.question.register.QuestionRelatedMemberProvider;
import com.hanpyeon.academyapi.board.service.question.update.QuestionUpdateManager;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionValidateManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionValidateManager questionValidateManager;
    private final QuestionRelatedMemberProvider questionRelatedMemberProvider;
    private final ImageService imageService;
    private final BoardMapper boardMapper;
    private final QuestionUpdateManager questionUpdateManager;

    @Transactional
    @WarnLoggable
    public Long addQuestion(@Validated final QuestionRegisterDto questionDto) {
        QuestionRelatedMember questionMember = questionRelatedMemberProvider.getQuestionRelatedMember(questionDto);
        List<Image> imageSources = imageService.saveImage(questionDto.images());
        Question newQuestion = boardMapper.createEntity(questionDto, questionMember.requestMember(), questionMember.targetMember(), imageSources);

        questionValidateManager.validate(newQuestion);
        return questionRepository.save(newQuestion).getId();
    }

    @WarnLoggable
    public QuestionDetails getSingleQuestionDetails(final Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
        question.addViewCount();
        return boardMapper.createQuestionDetails(question);
    }

    public Slice<QuestionPreview> loadLimitedQuestions(final Pageable pageable) {
        return questionRepository.findBy(pageable)
                .map(boardMapper::createQuestionPreview);
    }

    @Transactional
    public Long updateQuestion(@Validated final QuestionUpdateDto questionUpdateDto) {
        Question targetQuestion = findQuestion(questionUpdateDto.questionId());
        if (!questionUpdateDto.requestMemberId().equals(targetQuestion.getOwnerMember().getId())) {
            throw new RequestDeniedException("본인 질문이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
        questionUpdateManager.updateQuestion(targetQuestion, questionUpdateDto);

        questionValidateManager.validate(targetQuestion);
        return targetQuestion.getId();
    }

    @Transactional
    public void deleteQuestion(@Validated final QuestionDeleteDto questionDeleteDto) {
        final Question question = findQuestion(questionDeleteDto.questionId());
        imageService.removeImage(question.getImages());
        questionRepository.delete(question);
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
