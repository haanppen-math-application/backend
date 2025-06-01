package com.hpmath.domain.board.comment;

import com.hpmath.client.member.MemberClient;
import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.ErrorCode;
import com.hpmath.domain.board.comment.dto.CommentDetailResult;
import com.hpmath.domain.board.comment.dto.CommentQueryCommand;
import com.hpmath.domain.board.comment.dto.MemberDetailResult;
import com.hpmath.domain.board.comment.exception.CommentException;
import com.hpmath.domain.board.comment.repository.CommentRepository;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
    private final CommentRepository commentRepository;
    private final MemberInfoManager memberInfoManager;

    @Cacheable(cacheNames = "board::question::comment::list", key = "#command.questionId()")
    public List<CommentDetailResult> commentDetailResults(@Valid final CommentQueryCommand command) {
        return commentRepository.queryCommentsWithMedias(command.questionId()).stream()
                .map(comment -> {
                    final MemberDetailResult memberDetailResult = memberInfoManager.get(comment.getOwnerId());
                    return CommentDetailResult.from(comment, memberDetailResult);}
                )
                .toList();
    }

    @Cacheable(cacheNames = "board::comment::detail", key = "#commentId")
    public CommentDetailResult commentDetailResult(final Long commentId) {
        return commentRepository.querySingleCommentById(commentId)
                .map(comment -> {
                    final MemberDetailResult memberDetailResult = memberInfoManager.get(comment.getOwnerId());
                    return CommentDetailResult.from(comment, memberDetailResult);})
                .orElseThrow(() -> new CommentException(ErrorCode.NO_SUCH_COMMENT));
    }
}
