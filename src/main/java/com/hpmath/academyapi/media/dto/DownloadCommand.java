package com.hpmath.academyapi.media.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DownloadCommand {
    private final String fileName;
}
