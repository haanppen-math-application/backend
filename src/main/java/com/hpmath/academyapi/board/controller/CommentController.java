package com.hpmath.academyapi.board.controller;

import com.hpmath.academyapi.board.controller.Requests.CommentRegisterRequest;
import com.hpmath.academyapi.board.controller.Requests.CommentUpdateRequest;
import com.hpmath.academyapi.board.dto.CommentDeleteCommand;
import com.hpmath.academyapi.board.dto.CommentRegisterCommand;
import com.hpmath.academyapi.board.dto.CommentUpdateCommand;
import com.hpmath.academyapi.board.service.comment.CommentRegisterService;
import com.hpmath.academyapi.board.service.comment.CommentService;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<?> addComment(
            @Valid @RequestBody final CommentRegisterRequest commentRegisterRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
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
    public ResponseEntity<?> updateComment(
            @Valid @RequestBody final CommentUpdateRequest commentUpdateRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final CommentUpdateCommand commentUpdateDto = commentUpdateRequest.toCommand(memberPrincipal.memberId(),
                memberPrincipal.role());
        commentService.updateComment(commentUpdateDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제할 수 있는 API 입니다")
    @DeleteMapping("/{commentId}")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> deleteComment(
            @NotNull @PathVariable final Long commentId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final CommentDeleteCommand commentDeleteDto = new CommentDeleteCommand(commentId, memberPrincipal.memberId(),
                memberPrincipal.role());
        commentService.deleteComment(commentDeleteDto);

        return ResponseEntity.noContent().build();
    }
}
