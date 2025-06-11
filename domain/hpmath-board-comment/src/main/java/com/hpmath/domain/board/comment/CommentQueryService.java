package com.hpmath.domain.board.comment;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.board.comment.dto.CommentDetailResult;
import com.hpmath.domain.board.comment.dto.CommentDetailResults;
import com.hpmath.domain.board.comment.dto.CommentQueryCommand;
import com.hpmath.domain.board.comment.dto.MemberDetailResult;
import com.hpmath.domain.board.comment.exception.CommentException;
import com.hpmath.domain.board.comment.repository.CommentRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class CommentQueryService {
    private final CommentRepository commentRepository;
    private final MemberInfoManager memberInfoManager;

    @Cacheable(cacheNames = "board::question::comment::list", key = "#command.questionId()")
    public CommentDetailResults commentDetailResults(@Valid final CommentQueryCommand command) {
        final List<CommentDetailResult> results = commentRepository.queryCommentsWithMedias(command.questionId()).stream()
                .map(comment -> {
                    final MemberDetailResult memberDetailResult = memberInfoManager.get(comment.getOwnerId());
                    return CommentDetailResult.from(comment, memberDetailResult);}
                )
                .toList();
        return new CommentDetailResults(results);
    }

    @Cacheable(cacheNames = "board::comment::detail", key = "#commentId")
    public CommentDetailResult commentDetailResult(@NotNull final Long commentId) {
        return commentRepository.querySingleCommentById(commentId)
                .map(comment -> {
                    final MemberDetailResult memberDetailResult = memberInfoManager.get(comment.getOwnerId());
                    return CommentDetailResult.from(comment, memberDetailResult);})
                .orElseThrow(() -> new CommentException(ErrorCode.NO_SUCH_COMMENT));
    }
}
