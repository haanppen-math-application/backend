package com.hpmath.academyapi.dir.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FileView {
    private final String fileName;
    private final Boolean isDir;
    private final String path;
    private final LocalDateTime createdTime;
    private final Boolean canViewByEveryone;
    private final Boolean canModifyByEveryone;
}
