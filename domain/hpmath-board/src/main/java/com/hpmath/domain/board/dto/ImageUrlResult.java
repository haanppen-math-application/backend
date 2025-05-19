package com.hpmath.domain.board.dto;

public record ImageUrlResult(
        String imageUrl
) {
    public static ImageUrlResult from(String imageUrl) {
        return new ImageUrlResult(imageUrl);
    }
}
