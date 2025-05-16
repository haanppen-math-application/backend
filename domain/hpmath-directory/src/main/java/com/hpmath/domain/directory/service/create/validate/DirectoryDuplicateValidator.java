package com.hpmath.domain.directory.service.create.validate;

import com.hpmath.domain.directory.dao.Directory;
import com.hpmath.domain.directory.dao.DirectoryRepository;
import com.hpmath.domain.directory.exception.DirectoryException;
import com.hpmath.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DirectoryDuplicateValidator implements DirectoryCreateValidator {

    private final DirectoryRepository directoryRepository;

    @Override
    public void validate(final Directory directory) {
        if (directoryRepository.findDirectoryByPath(directory.getPath()).isPresent()) {
            throw new DirectoryException(ErrorCode.ALREADY_EXISTS_DIRECTORY_PATH);
        }
    }
}
