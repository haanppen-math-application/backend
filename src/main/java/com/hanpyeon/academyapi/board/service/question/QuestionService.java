package com.hanpyeon.academyapi.board.service.question;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.question.access.QuestionAccessManager;
import com.hanpyeon.academyapi.board.service.question.delete.QuestionDeleteManager;
import com.hanpyeon.academyapi.board.service.question.register.QuestionRegisterManger;
import com.hanpyeon.academyapi.board.service.question.update.QuestionUpdateManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.paging.PagedResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Component
@AllArgsConstructor
@Transactional
public class QuestionService {
    private final QuestionRegisterManger questionRegisterManager;
    private final QuestionUpdateManager questionUpdateManager;
    private final QuestionDeleteManager questionDeleteManager;
    private final QuestionAccessManager questionAccessManager;
    private final BoardMapper boardMapper;

    private final QuestionRepository questionRepository;

    @Transactional
    @WarnLoggable
    public Long addQuestion(@Validated final QuestionRegisterDto questionRegisterDto) {
        final Question question = questionRegisterManager.register(questionRegisterDto);
        return questionRepository.save(question).getId();
    }

    @WarnLoggable
    public QuestionDetails getSingleQuestionDetails(final Long questionId) {
        final Question question = findQuestion(questionId);
        return questionAccessManager.getSingle(question);
    }

    @Transactional(readOnly = true)
    public PagedResponse<QuestionPreview> loadQuestionsByOffset(final Pageable pageable, final String title) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findAllBy(pageable);
        }else {
            questions = questionRepository.findAllByTitleContaining(title, pageable);
        }
        return PagedResponse.of(questions.map(question -> boardMapper.createQuestionPreview(question)));
    }

    @Transactional(readOnly = true)
    public PagedResponse<QuestionPreview> loadMyQuestionsByOffset(final Long memberId, final Pageable pageable, final String title) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findQuestionsByOwnerMemberId(memberId, pageable);
        } else {
            questions = questionRepository.findQuestionsByOwnerMemberIdAndTitleContaining(memberId, title, pageable);
        }
        return PagedResponse.of(questions.map(question -> boardMapper.createQuestionPreview(question)));
    }

    @Transactional
    public Long updateQuestion(@Validated final QuestionUpdateDto questionUpdateDto) {
        final Question targetQuestion = findQuestion(questionUpdateDto.questionId());
        questionUpdateManager.update(targetQuestion, questionUpdateDto);
        return targetQuestion.getId();
    }

    @Transactional
    public void deleteQuestion(@Validated final QuestionDeleteDto questionDeleteDto) {
        final Question question = findQuestion(questionDeleteDto.questionId());
        questionDeleteManager.delete(question, questionDeleteDto.requestMemberId());
        questionRepository.delete(question);
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
