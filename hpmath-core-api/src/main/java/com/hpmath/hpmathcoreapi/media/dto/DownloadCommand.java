package com.hpmath.hpmathcoreapi.media.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DownloadCommand {
    private final String fileName;
}
