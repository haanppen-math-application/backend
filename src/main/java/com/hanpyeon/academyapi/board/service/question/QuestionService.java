package com.hanpyeon.academyapi.board.service.question;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.controller.Responses.QuestionDetails;
import com.hanpyeon.academyapi.board.controller.Responses.QuestionPreview;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.QuestionDeleteCommand;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterCommand;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.paging.PagedResponse;
import com.hanpyeon.academyapi.security.Role;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
@Transactional
public class QuestionService {
    private final QuestionRegisterService questionRegisterService;
    private final QuestionUpdateManager questionUpdateManager;
    private final QuestionDeleteService questionDeleteManager;
    private final QuestionQueryService questionAccessManager;
    private final BoardMapper boardMapper;

    private final QuestionRepository questionRepository;

    @Transactional
    @WarnLoggable
    public Long addQuestion(@Validated final QuestionRegisterCommand questionRegisterDto) {
        final Question question = questionRegisterService.register(questionRegisterDto);
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
    public Long updateQuestion(@Validated final QuestionUpdateCommand questionUpdateDto) {
        final Question targetQuestion = findQuestion(questionUpdateDto.questionId());
        questionUpdateManager.update(targetQuestion, questionUpdateDto);
        return targetQuestion.getId();
    }

    @Transactional
    public void deleteQuestion(@Validated final QuestionDeleteCommand questionDeleteDto) {
        final Question question = findQuestion(questionDeleteDto.questionId());
        validate(question, questionDeleteDto);
        questionDeleteManager.delete(question, questionDeleteDto.requestMemberId());
        questionRepository.delete(question);
    }

    private void validate(final Question question, final QuestionDeleteCommand questionDeleteDto) {
        if (questionDeleteDto.role().equals(Role.STUDENT)) {
            if (question.getOwnerMember().getId().equals(questionDeleteDto.requestMemberId())) {
                return;
            }
            throw new CourseException("학생은 본인 질문만 삭제할 수 있습니다", ErrorCode.INVALID_COURSE_ACCESS);
        }
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
