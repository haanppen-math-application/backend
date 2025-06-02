package com.hpmath.domain.media.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DownloadCommand {
    @NotNull
    private final String fileName;
}
