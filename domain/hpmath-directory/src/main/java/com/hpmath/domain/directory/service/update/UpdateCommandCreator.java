package com.hpmath.domain.directory.service.update;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.UpdateDirectoryCommand;
import com.hpmath.domain.directory.dto.UpdateDirectoryDto;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.form.DirectoryPathFormResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCommandCreator {
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryRepository directoryRepository;

    public UpdateDirectoryCommand getUpdateCommand(final UpdateDirectoryDto updateDirectoryDto) {
        final Directory directory = getDirectory(updateDirectoryDto.targetDirPath());
        return new UpdateDirectoryCommand(directory, updateDirectoryDto.newDirName(), updateDirectoryDto.requestMemberId());
    }

    private Directory getDirectory(final String targetDir) {
        final String resolvedPath = directoryPathFormResolver.resolveToAbsolutePath(targetDir);
        return directoryRepository.findDirectoryByPath(resolvedPath)
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
    }
}
