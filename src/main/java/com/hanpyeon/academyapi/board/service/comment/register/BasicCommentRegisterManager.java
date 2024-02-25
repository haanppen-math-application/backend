package com.hanpyeon.academyapi.board.service.comment.register;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.stereotype.Component;

@Component
class BasicCommentRegisterManager extends AbstractCommentRegisterManager {
    public BasicCommentRegisterManager(ImageService imageService, QuestionRepository questionRepository, MemberRepository memberRepository, BoardMapper boardMapper) {
        super(imageService, questionRepository, memberRepository, boardMapper);
    }

    @Override
    protected void verifyQuestion(Question quesㅎtion) {

    }

    @Override
    protected void verifyMember(Member member) {
        if (member.getRole().equals(Role.STUDENT)) {
            throw new RequestDeniedException("학생은 질문에 대한 댓글을 달 수 없습니다", ErrorCode.DENIED_EXCEPTION);
        }
    }
}
