package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.account.entity.Member;

public record QuestionRelatedMember(Member requestMember, Member targetMember) {
}
