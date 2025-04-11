package com.hanpyeon.academyapi.board.service.question;

import com.hanpyeon.academyapi.board.dao.CommentRepository;
import com.hanpyeon.academyapi.board.dao.MemberManager;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.QuestionDeleteCommand;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
public class QuestionDeleteService {
    private final QuestionRepository questionRepository;
    private final MemberManager memberManager;
    private final CommentRepository commentRepository;
    private final ImageService imageService;

    @Transactional
    public void deleteQuestion(@Validated final QuestionDeleteCommand questionDeleteDto) {
        final Question question = findQuestion(questionDeleteDto.questionId());
        validate(question, questionDeleteDto);
        this.delete(question, questionDeleteDto.requestMemberId());
        questionRepository.delete(question);
    }

    @Transactional
    public void delete(final Question question, final Long requestMemberId) {
        validate(requestMemberId, question.getOwnerMember().getId());
        question.getComments().stream()
                        .forEach(comment -> {
                            comment.delete();
                            commentRepository.delete(comment);
                        });
        imageService.removeImage(question.getImages());
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }

    private void validate(final Question question, final QuestionDeleteCommand questionDeleteDto) {
        if (questionDeleteDto.role().equals(Role.STUDENT)) {
            if (question.getOwnerMember().getId().equals(questionDeleteDto.requestMemberId())) {
                return;
            }
            throw new CourseException("학생은 본인 질문만 삭제할 수 있습니다", ErrorCode.INVALID_COURSE_ACCESS);
        }
    }

    private void validate(final Long requestMemberId, final Long questionOwnerId) {
        if (requestMemberId.equals(questionOwnerId) || Objects.nonNull(memberManager.getMemberWithRoleValidated(requestMemberId, Role.MANAGER, Role.TEACHER))) {
            return;
        }
        throw new RequestDeniedException(ErrorCode.DENIED_EXCEPTION);
    }
}
