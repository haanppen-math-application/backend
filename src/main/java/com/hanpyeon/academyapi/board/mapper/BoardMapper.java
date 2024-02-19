package com.hanpyeon.academyapi.board.mapper;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.entity.Question;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class BoardMapper {
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
                        .map(this::createImageUrlDto)
                        .toList())
                .comments(question.getComments().stream()
                        .map(this::createCommentDetails)
                        .toList())
                .build();
    }

    public ImageUrlDto createImageUrlDto(final Image image) {
        return ImageUrlDto.builder()
                .imageUrl(image.getSrc())
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
                        .map(this::createImageUrlDto)
                        .toList())
                .registeredDateTime(comment.getRegisteredDateTime())
                .registeredMemberDetails(createMemberDetails(comment.getRegisteredMember()))
                .build();
    }
}
