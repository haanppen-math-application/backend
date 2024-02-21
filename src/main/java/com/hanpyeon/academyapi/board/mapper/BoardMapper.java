package com.hanpyeon.academyapi.board.mapper;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.media.MediaMapper;
import com.hanpyeon.academyapi.media.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardMapper {

    private final MediaMapper mediaMapper;

    public QuestionRegisterDto createRegisterDto(final QuestionRegisterRequestDto questionRegisterRequestDto, final List<MultipartFile> multipartFiles, Long userId) {
        return QuestionRegisterDto.builder()
                .title(questionRegisterRequestDto.title())
                .content(questionRegisterRequestDto.content())
                .requestMemberId(userId)
                .targetMemberId(questionRegisterRequestDto.targetMemberId())
                .images(multipartFiles)
                .build();
    }

    public Question createEntity(final QuestionRegisterDto questionRegisterDto, final Member requestMember, final Member targetMember, final List<Image> images) {
        return Question.builder()
                .title(questionRegisterDto.title())
                .content(questionRegisterDto.content())
                .ownerMember(requestMember)
                .targetMember(targetMember)
                .images(images)
                .build();
    }

    public QuestionDetails createQuestionDetails(final Question question) {
        return QuestionDetails.builder()
                .questionId(question.getId())
                .content(question.getContent())
                .solved(question.getSolved())
                .viewCount(question.getViewCount())
                .registeredMember(createMemberDetails(question.getOwnerMember()))
                .targetMember(createMemberDetails(question.getTargetMember()))
                .registeredDateTime(question.getRegisteredDateTime())
                .imageUrls(question.getImages().stream()
                        .map(mediaMapper::createImageUrlDto)
                        .toList())
                .comments(question.getComments().stream()
                        .map(this::createCommentDetails)
                        .toList())
                .build();
    }

    public MemberDetails createMemberDetails(final Member member) {
        return MemberDetails.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .memberGrade(member.getGrade())
                .role(member.getRole().getIdentifier())
                .build();
    }

    public CommentDetails createCommentDetails(final Comment comment) {
        return CommentDetails.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .selected(comment.getIsSelected())
                .images(comment.getImages().stream()
                        .map(mediaMapper::createImageUrlDto)
                        .toList())
                .registeredDateTime(comment.getRegisteredDateTime())
                .registeredMemberDetails(createMemberDetails(comment.getRegisteredMember()))
                .build();
    }

    public QuestionPreview createQuestionPreview(final Question question) {
        return QuestionPreview.builder()
                .questionId(question.getId())
                .content(question.getContent())
                .solved(question.getSolved())
                .images(question.getImages().stream()
                        .map(mediaMapper::createImageUrlDto)
                        .toList())
                .commentCount(question.getComments().size())
                .viewCount(question.getViewCount())
                .owner(createMemberDetails(question.getOwnerMember()))
                .build();
    }

    public Comment createComment(final Question question, final Member member, final List<Image> images, final String content) {
        return Comment.builder()
                .question(question)
                .content(content)
                .images(images)
                .registeredMember(member)
                .build();
    }
    public CommentSelectionUpdateDto createCommentSelectionUpdateDto(final CommentSelectionUpdateRequestDto requestDto, final Long memberId) {
        return CommentSelectionUpdateDto.builder()
                .requestMemberId(memberId)
                .commendId(requestDto.commentId())
                .status(requestDto.state())
                .build();
    }
    public CommentRegisterDto createCommentRegisterDto(final Long questionId, final CommentRegisterRequestDto registerRequestDto, final List<MultipartFile> images, final Long memberId) {
        return CommentRegisterDto.builder()
                .questionId(questionId)
                .content(registerRequestDto.content())
                .memberId(memberId)
                .images(images)
                .build();
    }

}
