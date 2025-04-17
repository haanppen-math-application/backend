package com.hpmath.academyapi.board.service.question;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.board.dao.MemberManager;
import com.hpmath.academyapi.board.dto.QuestionRegisterCommand;
import com.hpmath.academyapi.board.entity.Question;
import com.hpmath.academyapi.board.mapper.BoardMapper;
import com.hpmath.academyapi.board.service.question.validate.QuestionValidateManager;
import com.hpmath.academyapi.media.entity.Image;
import com.hpmath.academyapi.media.service.ImageService;
import com.hpmath.academyapi.security.Role;
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
