package com.hpmath.domain.board.comment;

import com.hpmath.client.member.MemberClient;
import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.ErrorCode;
import com.hpmath.domain.board.comment.dto.CommentDetailResult;
import com.hpmath.domain.board.comment.dto.CommentQueryCommand;
import com.hpmath.domain.board.comment.exception.CommentException;
import com.hpmath.domain.board.comment.repository.CommentRepository;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
    private final CommentRepository commentRepository;
    private final MemberClient memberClient;

    public List<CommentDetailResult> commentDetailResults(@Valid final CommentQueryCommand command) {
        return commentRepository.queryCommentsWithMedias(command.questionId()).stream()
                .map(comment -> {
                    final MemberInfo info = memberClient.getMemberDetail(comment.getOwnerId());
                    return CommentDetailResult.from(comment, info);}
                )
                .toList();
    }

    public CommentDetailResult commentDetailResult(final Long commentId) {
        return commentRepository.querySingleCommentById(commentId)
                .map(comment -> {
                    final MemberInfo info = memberClient.getMemberDetail(comment.getOwnerId());
                    return CommentDetailResult.from(comment, info);})
                .orElseThrow(() -> new CommentException(ErrorCode.NO_SUCH_COMMENT));
    }
}
