package com.hanpyeon.academyapi.board.service.comment;

import com.hanpyeon.academyapi.board.dto.CommentRegisterDto;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.repository.CommentRepository;
import com.hanpyeon.academyapi.board.service.comment.register.AbstractCommentRegisterManager;
import com.hanpyeon.academyapi.board.service.comment.register.CommentRegisterManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentRegisterManager commentRegisterManager;

    @Transactional
    public Long addComment(@Validated final CommentRegisterDto commentRegisterDto) {
        Comment comment = commentRegisterManager.register(commentRegisterDto);
        commentRepository.save(comment);
        return comment.getId();
    }
}
