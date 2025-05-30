package com.hpmath.app.api.board.comment;

import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.board.comment.CommentService;
import com.hpmath.domain.board.comment.dto.DeleteCommentCommand;
import com.hpmath.domain.board.comment.dto.RegisterCommentCommand;
import com.hpmath.domain.board.comment.dto.UpdateCommentCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/comments")
public class BoardCommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 등록 API", description = "질문 게시글에 댓글을 달 수 있도록 하는 API 입니다.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> addComment(
            @Valid @RequestBody final Requests.RegisterCommentRequest commentRegisterRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final RegisterCommentCommand command = commentRegisterRequest.toCommand(memberPrincipal.memberId());
        final Long createdCommentId = commentService.addComment(command);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdCommentId)
                        .toUri()
        ).build();
    }

    @Operation(summary = "댓글 내용 수정 API")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateComment(
            @Valid @RequestBody final Requests.UpdateCommentRequest commentUpdateRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final UpdateCommentCommand command = commentUpdateRequest.toCommand(memberPrincipal.memberId(),
                memberPrincipal.role());
        commentService.updateComment(command);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제할 수 있는 API 입니다")
    @DeleteMapping("/{commentId}")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> deleteComment(
            @NotNull @PathVariable final Long commentId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final DeleteCommentCommand command = new DeleteCommentCommand(commentId, memberPrincipal.memberId(),
                memberPrincipal.role());
        commentService.deleteComment(command);

        return ResponseEntity.noContent().build();
    }
}
