package com.hpmath.domain.media.dto;

import java.time.LocalDateTime;

public record MediaInfo(
        String mediaName,
        String mediaSrc,
        LocalDateTime createdTime,
        Long runtimeDuration,
        Long fileSize
){
}
