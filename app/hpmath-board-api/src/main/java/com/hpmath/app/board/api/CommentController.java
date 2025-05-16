package com.hpmath.app.board.api;

import com.hpmath.app.board.api.Requests.CommentRegisterRequest;
import com.hpmath.app.board.api.Requests.CommentUpdateRequest;
import com.hpmath.domain.board.dto.CommentDeleteCommand;
import com.hpmath.domain.board.dto.CommentRegisterCommand;
import com.hpmath.domain.board.dto.CommentUpdateCommand;
import com.hpmath.domain.board.service.comment.CommentRegisterService;
import com.hpmath.domain.board.service.comment.CommentService;
import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@RequestMapping("/api/board/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentRegisterService commentRegisterService;

    @Operation(summary = "댓글 등록 API", description = "질문 게시글에 댓글을 달 수 있도록 하는 API 입니다.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<?> addComment(
            @Valid @RequestBody final CommentRegisterRequest commentRegisterRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final CommentRegisterCommand commentRegisterDto = commentRegisterRequest.toCommand(memberPrincipal.memberId());
        final Long createdCommentId = commentRegisterService.register(commentRegisterDto);

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
    public ResponseEntity<?> updateComment(
            @Valid @RequestBody final CommentUpdateRequest commentUpdateRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final CommentUpdateCommand commentUpdateDto = commentUpdateRequest.toCommand(memberPrincipal.memberId(),
                memberPrincipal.role());
        commentService.updateComment(commentUpdateDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제할 수 있는 API 입니다")
    @DeleteMapping("/{commentId}")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<?> deleteComment(
            @NotNull @PathVariable final Long commentId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final CommentDeleteCommand commentDeleteDto = new CommentDeleteCommand(commentId, memberPrincipal.memberId(),
                memberPrincipal.role());
        commentService.deleteComment(commentDeleteDto);

        return ResponseEntity.noContent().build();
    }
}
