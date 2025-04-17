package com.hpmath.hpmathcoreapi.board.mapper;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.board.controller.Responses.CommentDetails;
import com.hpmath.hpmathcoreapi.board.controller.Responses.MemberDetails;
import com.hpmath.hpmathcoreapi.board.controller.Responses.QuestionDetails;
import com.hpmath.hpmathcoreapi.board.controller.Responses.QuestionPreview;
import com.hpmath.hpmathcoreapi.board.entity.Comment;
import com.hpmath.hpmathcoreapi.board.entity.Question;
import com.hpmath.hpmathcoreapi.media.MediaMapper;
import com.hpmath.hpmathcoreapi.media.entity.Image;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardMapper {

    private final MediaMapper mediaMapper;

    public Question createQuestion(final Member requestMember, final Member targetMember, final List<Image> images, final String title, final String content) {
        return Question.builder()
                .title(title)
                .content(content)
                .ownerMember(requestMember)
                .targetMember(targetMember)
                .images(images)
                .build();
    }

    public QuestionDetails createQuestionDetails(final Question question) {
        return QuestionDetails.builder()
                .questionId(question.getId())
                .title(question.getTitle())
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
        if (Objects.isNull(member)) {
            return null;
        }
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
                .selected(comment.getAdopted())
                .images(comment.getImages().stream()
                        .map(mediaMapper::createImageUrlDto)
                        .toList())
                .registeredDateTime(comment.getRegisteredDateTime())
                .content(comment.getContent())
                .registeredMemberDetails(createMemberDetails(comment.getRegisteredMember()))
                .build();
    }

    public QuestionPreview createQuestionPreview(final Question question) {
        return QuestionPreview.builder()
                .title(question.getTitle())
                .registeredDateTime(question.getRegisteredDateTime())
                .questionId(question.getId())
                .solved(question.getSolved())
                .commentCount(question.getComments().size())
                .viewCount(question.getViewCount())
                .owner(createMemberDetails(question.getOwnerMember()))
                .target(createMemberDetails(question.getTargetMember()))
                .build();
    }

    public Comment createComment(final Question question, final Member member, final List<Image> images, final String content) {
        return Comment.builder()
                .question(question)
                .images(images)
                .registeredMember(member)
                .content(content)
                .build();
    }
}
