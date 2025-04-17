package com.hpmath.hpmathcoreapi.dir.service.create.validate;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import com.hpmath.hpmathcoreapi.dir.dao.DirectoryRepository;
import com.hpmath.hpmathcoreapi.dir.exception.DirectoryException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
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
