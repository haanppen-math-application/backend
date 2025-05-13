package com.hpmath.domain.directory.dto;

import com.hpmath.domain.directory.entity.CommentImage;
import com.hpmath.domain.directory.entity.QuestionImage;

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
