package com.hanpyeon.academyapi.dir.service.create;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class DirectoryCreateCommandCreator {
    private final MemberRepository memberRepository;

    public CreateDirectoryCommand create(@Validated final CreateDirectoryDto createDirectoryDto) {
        final String dirPath = getDirPath(createDirectoryDto.directoryPath());
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(createDirectoryDto.ownerId())
                .orElseThrow(() -> new DirectoryException(ErrorCode.CANNOT_FIND_USER));
        return new CreateDirectoryCommand(dirPath, createDirectoryDto.directoryName(), getAbsolutePath(dirPath, createDirectoryDto.directoryName()), createDirectoryDto.canViewByEveryone(), member);
    }

    private String getDirPath(final String dirPath) {
        if (dirPath.contains("//")) {
            throw new DirectoryException(dirPath, ErrorCode.ILLEGAL_PATH);
        }
        if (dirPath.charAt(dirPath.length() - 1) != '/') {
            return dirPath + "/";
        }
        return dirPath;
    }

    private String getAbsolutePath(final String dirPath, final String dirName) {
        return dirPath + dirName + "/";
    }
}
