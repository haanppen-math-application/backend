package com.hpmath.domain.board.service.update;

import com.hpmath.domain.board.dao.Directory;
import com.hpmath.domain.board.dao.DirectoryRepository;
import com.hpmath.domain.board.dto.UpdateDirectoryCommand;
import com.hpmath.domain.board.dto.UpdateDirectoryDto;
import com.hpmath.domain.board.exception.DirectoryException;
import com.hpmath.domain.board.service.form.resolver.DirectoryPathFormResolver;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UpdateCommandCreator {
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
