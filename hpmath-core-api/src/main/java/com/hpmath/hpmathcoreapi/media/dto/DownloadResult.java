package com.hpmath.hpmathcoreapi.media.dto;

import java.io.InputStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DownloadResult {
    private final InputStream inputStream;
    private final String fileName;
}
