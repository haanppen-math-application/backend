package com.hpmath.hpmathcoreapi.media.dto;

import java.io.InputStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChunkedMediaDto {
    private final InputStream inputStream;
    private final Long currentSize;
    private final Long startIndex;
    private final Long endIndex;
}
