package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.service.question.register.QuestionRelatedMemberProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class QuestionTargetUpdateHandler extends QuestionUpdateHandler {
    private final QuestionRelatedMemberProvider questionRelatedMemberProvider;

    @Override
    boolean applicable(QuestionUpdateDto questionUpdateDto) {
        return questionUpdateDto.targetMemberId() != null;
    }

    @Override
    void process(Question question, QuestionUpdateDto questionUpdateDto) {
        Member member = questionRelatedMemberProvider.getUpperTeacherTargetMember(questionUpdateDto.targetMemberId());
        question.changeTargetMember(member);
    }
}
