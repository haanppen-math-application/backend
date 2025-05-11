package com.hpmath.hpmathcoreapi.board.service.question;

import com.hpmath.domain.member.Member;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.board.dao.MemberManager;
import com.hpmath.hpmathcoreapi.board.dto.QuestionRegisterCommand;
import com.hpmath.hpmathcoreapi.board.entity.Question;
import com.hpmath.hpmathcoreapi.board.mapper.BoardMapper;
import com.hpmath.hpmathcoreapi.board.service.question.validate.QuestionValidateManager;
import com.hpmath.hpmathmediadomain.media.entity.Image;
import com.hpmath.hpmathmediadomain.media.service.ImageService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuestionRegisterService {
    private final QuestionValidateManager questionValidateManager;
    private final MemberManager memberManager;
    private final ImageService imageService;
    private final BoardMapper boardMapper;

    @Transactional
    public Long addQuestion(final QuestionRegisterCommand questionRegisterDto) {
        final Question question = create(questionRegisterDto);
        questionValidateManager.validate(question);
        return question.getId();
    }

    private Question create(final QuestionRegisterCommand questionRegisterDto) {
        final Member requestMember = memberManager.getMemberWithRoleValidated(questionRegisterDto.requestMemberId(), Role.STUDENT);
        Member targetMember;
        if (questionRegisterDto.targetMemberId() == null) {
            targetMember = null;
        } else {
            targetMember = memberManager.getMemberWithRoleValidated(questionRegisterDto.targetMemberId(), Role.TEACHER, Role.MANAGER);
        }
        final List<Image> images = imageService.loadImages(questionRegisterDto.images());

        return boardMapper.createQuestion(requestMember, targetMember, images, questionRegisterDto.title(), questionRegisterDto.content());
    }
}
