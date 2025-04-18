package com.hpmath.hpmathmediadomain.media.dto;

import org.springframework.web.multipart.MultipartFile;

public record ChunkUploadCommand(
        String uniqueId,
        int partNumber,
        MultipartFile multipartFile
) {
}
