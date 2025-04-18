package com.hpmath.hpmathcoreapi.board.service.question;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.board.dao.CommentRepository;
import com.hpmath.hpmathcoreapi.board.dao.MemberManager;
import com.hpmath.hpmathcoreapi.board.dao.QuestionRepository;
import com.hpmath.hpmathcoreapi.board.dto.QuestionDeleteCommand;
import com.hpmath.hpmathcoreapi.board.entity.Question;
import com.hpmath.hpmathcoreapi.board.exception.NoSuchQuestionException;
import com.hpmath.hpmathcoreapi.course.application.exception.CourseException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.media.service.ImageService;
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
