package com.hpmath.domain.media.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DownloadCommand {
    private final String fileName;
}
