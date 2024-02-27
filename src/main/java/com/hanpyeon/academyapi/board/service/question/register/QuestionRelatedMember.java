package com.hanpyeon.academyapi.board.service.question.register;

import com.hanpyeon.academyapi.account.entity.Member;

public record QuestionRelatedMember(Member requestMember, Member targetMember) {
}
