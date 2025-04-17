package com.hpmath.academyapi.board.service.question;

import com.hpmath.academyapi.board.dao.CommentRepository;
import com.hpmath.academyapi.board.dao.MemberManager;
import com.hpmath.academyapi.board.dao.QuestionRepository;
import com.hpmath.academyapi.board.dto.QuestionDeleteCommand;
import com.hpmath.academyapi.board.entity.Question;
import com.hpmath.academyapi.board.exception.NoSuchQuestionException;
import com.hpmath.academyapi.course.application.exception.CourseException;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.media.service.ImageService;
import com.hpmath.academyapi.security.Role;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class QuestionDeleteService {
    private final QuestionRepository questionRepository;
    private final MemberManager memberManager;
    private final CommentRepository commentRepository;
    private final ImageService imageService;

    @Transactional
    public void deleteQuestion(@Valid final QuestionDeleteCommand questionDeleteDto) {
        final Question question = findQuestion(questionDeleteDto.questionId());
        hasPermission(questionDeleteDto.requestMemberId(), question.getOwnerMember().getId());
        question.getComments().stream()
                .forEach(comment -> {
                    comment.delete();
                    commentRepository.delete(comment);
                });
        imageService.removeImage(question.getImages());
        questionRepository.delete(question);
    }

    private void hasPermission(final Long requestMemberId, final Long questionOwnerId) {
        if (requestMemberId.equals(questionOwnerId) || Objects.nonNull(memberManager.getMemberWithRoleValidated(requestMemberId, Role.MANAGER, Role.TEACHER))) {
            return;
        }
        throw new CourseException("삭제 권한 부재", ErrorCode.DENIED_EXCEPTION);
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
