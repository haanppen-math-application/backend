package com.hanpyeon.academyapi.dir.dto;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.validation.Valid;

public record CreateDirectoryCommand(
        String dirPath,
        String newDirName,
        String newDirAbsolutePath,
        Boolean canViewByEveryone,
        Member requestMember) {
}
