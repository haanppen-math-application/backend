package com.hpmath.domain.board.dto;

import com.hpmath.domain.board.entity.CommentImage;
import com.hpmath.domain.board.entity.QuestionImage;

public record ImageUrlResult(
        String imageUrl
) {
    public static ImageUrlResult from(QuestionImage questionImage) {
        return new ImageUrlResult(questionImage.getImageSrc());
    }

    public static ImageUrlResult from(CommentImage commentImage) {
        return new ImageUrlResult(commentImage.getImageSrc());
    }
}
