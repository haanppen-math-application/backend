package com.hpmath.domain.directory.service;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.CreateDirectoryCommand;
import com.hpmath.domain.directory.service.create.DirectoryCreationValidateManager;
import com.hpmath.domain.directory.service.form.DirectoryPathFormResolver;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DirectoryCreateService {
    private final DirectoryRepository directoryRepository;
    private final DirectoryPathFormResolver directoryPathFormResolver;
    private final DirectoryCreationValidateManager directoryCreationValidateManager;

    @Transactional
    public void addNewDirectory(@Valid final CreateDirectoryCommand createDirectoryDto) {
        final String absoluteDirPath = getAbsoluteDirPath(createDirectoryDto);

        final Directory directory = Directory.of(
                createDirectoryDto.ownerId(),
                absoluteDirPath,
                createDirectoryDto.canModifyByEveryone(),
                createDirectoryDto.canViewByEveryone());
        directoryCreationValidateManager.validate(directory);
        directoryRepository.save(directory);
    }

    private String getAbsoluteDirPath(final CreateDirectoryCommand createDirectoryDto) {
        final String parentDirPath = directoryPathFormResolver.resolveToAbsolutePath(createDirectoryDto.parentDirPath());
        return directoryPathFormResolver.resolveToAbsolutePath(parentDirPath, createDirectoryDto.directoryName());
    }
}
