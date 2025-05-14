package com.hpmath.domain.directory.dto;

import com.hpmath.hpmathcore.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DeleteDirectoryDto {
    private final String targetPath;
    private final Long requestMemberId;
    private final Role requestMemberRole;
    private final Boolean deleteChildes;
}
