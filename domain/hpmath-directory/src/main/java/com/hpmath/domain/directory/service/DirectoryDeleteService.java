package com.hpmath.domain.directory.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.DeleteDirectoryCommand;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.domain.directory.service.delete.DirectoryDeleteTargets;
import com.hpmath.domain.directory.service.delete.DeleteDirectoryProcessor;
import com.hpmath.domain.directory.service.form.DirectoryPathFormResolver;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DirectoryDeleteService {
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DeleteDirectoryProcessor deleteDirectoryProcessor;
    private final DirectoryRepository directoryRepository;

    @Transactional
    public void delete(@Valid final DeleteDirectoryCommand command) {
        final DirectoryDeleteTargets directoryDeleteTargets = loadDeleteTarget(command);

        deleteDirectoryProcessor.delete(directoryDeleteTargets);
    }

    private DirectoryDeleteTargets loadDeleteTarget(final DeleteDirectoryCommand deleteDirectoryDto) {
        final String absolutePath = directoryPathFormResolver.resolveToAbsolutePath(deleteDirectoryDto.targetPath());

        final Directory target = loadTarget(absolutePath);
        final List<Directory> allTargets = loadAllTargets(absolutePath);

        return DirectoryDeleteTargets.of(allTargets, target, deleteDirectoryDto.requestMemberId(), deleteDirectoryDto.deleteChildes(), deleteDirectoryDto.requestMemberRole());
    }

    private List<Directory> loadAllTargets(final String path) {
        return directoryRepository.queryChildDirectories(path);
    }

    private Directory loadTarget(final String path) {
        return directoryRepository.findDirectoryByPath(path)
                .orElseThrow(() -> new DirectoryException(path + " : 디렉토리를 찾을 수 없습니다", ErrorCode.NOT_EXIST_DIRECTORY));
    }
}
