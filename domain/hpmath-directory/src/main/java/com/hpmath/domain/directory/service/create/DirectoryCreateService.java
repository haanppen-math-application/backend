package com.hpmath.domain.directory.service.create;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.dto.CreateDirectoryCommand;
import com.hpmath.domain.directory.service.create.validate.DirectoryCreationValidateManager;
import com.hpmath.domain.directory.service.form.resolver.DirectoryPathFormResolver;
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
        final Directory directory = createDirectory(createDirectoryDto.ownerId(), absoluteDirPath, createDirectoryDto.canModifyByEveryone(), createDirectoryDto.canViewByEveryone());

        directoryCreationValidateManager.validate(directory);
        directoryRepository.save(directory);
    }

    private String getAbsoluteDirPath(CreateDirectoryCommand createDirectoryDto) {
        final String parentDirPath = directoryPathFormResolver.resolveToAbsolutePath(createDirectoryDto.parentDirPath());
        return directoryPathFormResolver.resolveToAbsolutePath(parentDirPath, createDirectoryDto.directoryName());
    }

    public Directory createDirectory(final Long requestMemberId, final String newDirPath,
                                     final boolean canModifyByEveryOne, final boolean canViewByEveryOne) {
        return new Directory(requestMemberId, newDirPath, canModifyByEveryOne, canViewByEveryOne);
    }
}
