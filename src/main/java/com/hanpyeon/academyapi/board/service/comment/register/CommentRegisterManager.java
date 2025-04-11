package com.hanpyeon.academyapi.board.service.comment.register;

import com.hanpyeon.academyapi.board.dto.CommentRegisterCommand;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.exception.NotAllowedCommentException;


public interface CommentRegisterManager {

    /**
     *
     * @param commentRegisterDto
     * @return Comment
     */
    Comment register(final CommentRegisterCommand commentRegisterDto) throws NotAllowedCommentException;
}
