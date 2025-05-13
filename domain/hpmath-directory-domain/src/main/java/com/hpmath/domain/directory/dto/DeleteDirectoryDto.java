package com.hpmath.domain.directory.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DeleteDirectoryDto {
    private final String targetPath;
    private final Long requestMemberId;
    private final Boolean deleteChildes;
}
