package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.repository.CommentRepository;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final QuestionRelatedMemberProvider questionRelatedMemberProvider;
    private final ImageService imageService;
    private final BoardMapper boardMapper;

    public void addQuestion(@Validated final QuestionRegisterDto questionDto) {
        QuestionRelatedMember questionMember = questionRelatedMemberProvider.getQuestionRelatedMember(questionDto);
        List<Image> imageSources = imageService.saveImage(questionDto.images());
        questionRepository.save(boardMapper.createEntity(
                questionDto,
                questionMember.requestMember(),
                questionMember.targetMember(),
                imageSources)
        );
    }

    @Transactional
    public CommentDetails addComment(@Validated final CommentRegisterDto commentRegisterDto) {
        Question question = questionRepository.findById(commentRegisterDto.questionId())
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
        Member member = question.getOwnerMember();
        if (member.getId().equals(commentRegisterDto.memberId())) {
            throw new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER);
        }
        List<Image> images = imageService.saveImage(commentRegisterDto.images());

        return boardMapper.createCommentDetails(
                commentRepository.save(boardMapper.createComment(question, member, images, commentRegisterDto.content()))
        );
    }

    public QuestionDetails getSingleQuestionDetails(final Long questionId) {
        return questionRepository.findById(questionId)
                .map(boardMapper::createQuestionDetails)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }

    public Slice<QuestionPreview> loadLimitedQuestions(final Pageable pageable) {
        return questionRepository.findBy(pageable)
                .map(boardMapper::createQuestionPreview);
    }
}
