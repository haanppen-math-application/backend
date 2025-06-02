package com.hpmath.domain.directory.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.UpdateDirectoryCommand;
import com.hpmath.domain.directory.dto.UpdateDirectoryDto;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.form.DirectoryPathFormResolver;
import com.hpmath.domain.directory.service.update.DirectoryUpdateManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DirectoryUpdateService {
    private final DirectoryUpdateManager directoryUpdateManager;
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryRepository directoryRepository;

    public void updateDirectory(@Valid final UpdateDirectoryDto updateDirectoryDto) {
        final UpdateDirectoryCommand updateDirectoryCommand = this.getUpdateCommand(updateDirectoryDto);
        directoryUpdateManager.update(updateDirectoryCommand);
    }

    public UpdateDirectoryCommand getUpdateCommand(final UpdateDirectoryDto updateDirectoryDto) {
        final String targetDirPath = directoryPathFormResolver.resolveToAbsolutePath(updateDirectoryDto.targetDirPath());
        final Directory directory = loadTarget(targetDirPath);

        return new UpdateDirectoryCommand(directory, updateDirectoryDto.newDirName(), updateDirectoryDto.requestMemberId());
    }

    private Directory loadTarget(final String targetDirPath) {
        return directoryRepository.findDirectoryByPath(targetDirPath)
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
    }
}
