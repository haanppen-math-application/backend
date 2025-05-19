package com.hpmath.app.api.board.comment;

import com.hpmath.domain.board.comment.CommentQueryService;
import com.hpmath.domain.board.comment.dto.CommentDetailResult;
import com.hpmath.domain.board.comment.dto.CommentQueryCommand;
import com.hpmath.domain.board.comment.dto.ImageUrlResult;
import com.hpmath.domain.board.comment.dto.MemberDetailResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inner/v1/board/comment")
@RequiredArgsConstructor
public class BoardCommentQueryController {
    private final CommentQueryService commentQueryService;

    @GetMapping
    public ResponseEntity<List<CommentDetailResponse>> loadQuestionsComments(
            @RequestParam final Long questionId
    ) {
        return ResponseEntity.ok(commentQueryService.commentDetailResults(new CommentQueryCommand(questionid)).stream()
                .map(CommentDetailResponse::from)
                .toList());
    }

    @GetMapping
    public ResponseEntity<CommentDetailResponse> loadSingleComment(
            @RequestParam final Long commentId
    ) {
        return ResponseEntity.ok(CommentDetailResponse.from(commentQueryService.commentDetailResult(commentId)));
    }

    private record CommentDetailResponse(
            Long commentId,
            String content,
            Boolean selected,
            List<ImageUrlResult> images,
            LocalDateTime registeredDateTime,
            MemberDetailResult registeredMemberDetails
    ) {
        public static CommentDetailResponse from(final CommentDetailResult result) {
            return new CommentDetailResponse(
                    result.commentId(),
                    result.content(),
                    result.selected(),
                    result.images(),
                    result.registeredDateTime(),
                    result.registeredMemberDetails());
        }
    }
}
