package com.hpmath.domain.directory.service.delete;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.DeleteDirectoryDto;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.form.resolver.DirectoryPathFormResolver;
import com.hpmath.common.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DirectoryDeleteCommandCreator {
    private final DirectoryRepository directoryRepository;
    private final DirectoryPathFormResolver directoryPathFormResolver;

    public DirectoryDeleteCommand create(final DeleteDirectoryDto deleteDirectoryDto) {
        final Directory target = findTargetDirectory(deleteDirectoryDto.getTargetPath());
        final List<Directory> childDirectories = getAssociatedDirectories(deleteDirectoryDto.getTargetPath());
        return new DirectoryDeleteCommand(childDirectories, target, deleteDirectoryDto.getRequestMemberId(), deleteDirectoryDto.getDeleteChildes(), deleteDirectoryDto.getRequestMemberRole());
    }

    private List<Directory> getAssociatedDirectories(final String targetPath) {
        final String path = directoryPathFormResolver.resolveToAbsolutePath(targetPath);
        final List<Directory> directories = directoryRepository.queryChildDirectories(path);
        return directories;
    }

    private Directory findTargetDirectory(final String targetPath) {
        final String path = directoryPathFormResolver.resolveToAbsolutePath(targetPath);
        return directoryRepository.findDirectoryByPath(path)
                .orElseThrow(() -> new DirectoryException(path + " : 디렉토리를 찾을 수 없습니다",ErrorCode.NOT_EXIST_DIRECTORY));
    }
}
