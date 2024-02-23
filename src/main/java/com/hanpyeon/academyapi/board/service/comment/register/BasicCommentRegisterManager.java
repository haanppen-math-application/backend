package com.hanpyeon.academyapi.board.service.comment.register;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import com.hanpyeon.academyapi.media.service.ImageService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BasicCommentRegisterManager extends AbstractCommentRegisterManager{
    public BasicCommentRegisterManager(ImageService imageService, QuestionRepository questionRepository, MemberRepository memberRepository, BoardMapper boardMapper) {
        super(imageService, questionRepository, memberRepository, boardMapper);
    }

    @Override
    protected void verifyQuestion(Question question) {
    }

    @Override
    protected void verifyMember(Member member) {
    }
}
