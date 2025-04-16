package com.hanpyeon.academyapi.media.dto;

import java.util.List;

public record RequiredChunkInfo(
        List<Integer> chunkNumbers
) {
}
