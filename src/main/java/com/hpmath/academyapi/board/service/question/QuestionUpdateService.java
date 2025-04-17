package com.hpmath.academyapi.board.service.question;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.board.dao.MemberManager;
import com.hpmath.academyapi.board.dao.QuestionRepository;
import com.hpmath.academyapi.board.dto.QuestionUpdateCommand;
import com.hpmath.academyapi.board.entity.Question;
import com.hpmath.academyapi.board.exception.NoSuchQuestionException;
import com.hpmath.academyapi.board.exception.RequestDeniedException;
import com.hpmath.academyapi.board.service.question.validate.QuestionValidateManager;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.media.entity.Image;
import com.hpmath.academyapi.media.service.ImageService;
import com.hpmath.academyapi.security.Role;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@AllArgsConstructor
@Validated
public class QuestionUpdateService {
    private final QuestionRepository questionRepository;
    private final MemberManager memberManager;
    private final ImageService imageService;
    private final QuestionValidateManager questionValidateManager;

    @Transactional
    public Long updateQuestion(@Valid final QuestionUpdateCommand questionUpdateDto) {
        final Question targetQuestion = findQuestion(questionUpdateDto.questionId());
        verifyAccess(targetQuestion.getOwnerMember().getId(), questionUpdateDto.requestMemberId(), questionUpdateDto.memberRole());

        updateTitle(targetQuestion, questionUpdateDto.title());
        updateContent(targetQuestion, questionUpdateDto.content());
        updateImages(targetQuestion, questionUpdateDto.imageSources());
        updateTarget(targetQuestion, questionUpdateDto.targetMemberId());

        questionValidateManager.validate(targetQuestion);
        return targetQuestion.getId();
    }

    private void updateTitle(final Question question, final String title) {
        question.changeTitle(title);
    }

    private void updateContent(final Question question, final String content) {
        question.changeContent(content);
    }

    private void updateImages(final Question question, final List<String> imageSrcs) {
        final List<Image> images = imageService.loadImages(imageSrcs);
        question.changeImages(images);
    }

    private void updateTarget(final Question question, final Long newTargetId) {
        Member member = memberManager.getMemberWithRoleValidated(newTargetId, Role.TEACHER, Role.MANAGER);
        question.changeTargetMember(member);
    }

    private void verifyAccess(final Long ownedMemberId, final Long requestMemberId, final Role role) {
        if (role.equals(Role.ADMIN) || role.equals(Role.TEACHER) || role.equals(Role.MANAGER)) {
            return;
        }
        if (!requestMemberId.equals(ownedMemberId)) {
            throw new RequestDeniedException("본인 질문이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
