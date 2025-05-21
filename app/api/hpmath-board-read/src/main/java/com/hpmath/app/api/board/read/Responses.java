package com.hpmath.app.api.board.read;

import com.hpmath.common.Role;
import com.hpmath.domain.board.read.dto.CommentDetailResult;
import com.hpmath.domain.board.read.dto.MemberDetailResult;
import com.hpmath.domain.board.read.dto.QuestionDetailResult;
import com.hpmath.domain.board.read.dto.QuestionPreviewResult;
import java.time.LocalDateTime;
import java.util.List;

public class Responses {
    record QuestionPreviewResponse(
            Long questionId,
            String title,
            LocalDateTime registeredDateTime,
            Boolean solved,
            Integer commentCount,
            Long viewCount,
            MemberDetailResponse owner,
            MemberDetailResponse target
    ) {
        public static QuestionPreviewResponse from(QuestionPreviewResult result) {
            return new QuestionPreviewResponse(
                    result.questionId(),
                    result.title(),
                    result.registeredDateTime(),
                    result.solved(),
                    result.commentCount(),
                    result.viewCount(),
                    MemberDetailResponse.from(result.owner()),
                    MemberDetailResponse.from(result.target())
            );
        }
    }

    record MemberDetailResponse(
            Long memberId,
            String memberName,
            Integer memberGrade,
            Role role
    ) {
        public static MemberDetailResponse from(MemberDetailResult result) {
            return new MemberDetailResponse(
                    result.memberId(),
                    result.memberName(),
                    result.memberGrade(),
                    result.role()
            );
        }

    }

    record QuestionDetailResponse(
            Long questionId,
            String title,
            String content,
            Boolean solved,
            Long viewCount,
            LocalDateTime registeredDateTime,
            MemberDetailResponse registeredMember,
            MemberDetailResponse targetMember,
            List<CommentDetailResponse> comments,
            List<ImageUrlResponse> imageUrls
    ) {
        public static QuestionDetailResponse from(QuestionDetailResult result) {
            return new QuestionDetailResponse(
                    result.questionId(),
                    result.title(),
                    result.content(),
                    result.solved(),
                    result.viewCount(),
                    result.registeredDateTime(),
                    MemberDetailResponse.from(result.owner()),
                    MemberDetailResponse.from(result.target()),
                    result.comments().stream()
                            .map(CommentDetailResponse::from)
                            .toList(),
                    result.imageUrls().stream()
                            .map(ImageUrlResponse::from)
                            .toList()
            );
        }
    }

    record CommentDetailResponse(
            Long commentId,
            String content,
            Boolean selected,
            List<ImageUrlResponse> images,
            LocalDateTime registeredDateTime,
            MemberDetailResponse registeredMemberDetails
    ) {
        public static CommentDetailResponse from(CommentDetailResult result) {
            return new CommentDetailResponse(
                    result.commentId(),
                    result.content(),
                    result.selected(),
                    result.images().stream()
                            .map(ImageUrlResponse::from)
                            .toList(),
                    result.registeredDateTime(),
                    MemberDetailResponse.from(result.registeredMemberDetails())
            );
        }
    }

    public record ImageUrlResponse(
            String imageUrl
    ) {
        public static ImageUrlResponse from(String imageUrl) {
            return new ImageUrlResponse(imageUrl);
        }
    }
}
