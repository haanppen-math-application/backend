package com.hpmath.academyapi.dir.service.create.validate;

import com.hpmath.academyapi.dir.dao.Directory;
import com.hpmath.academyapi.dir.dao.DirectoryRepository;
import com.hpmath.academyapi.dir.exception.DirectoryException;
import com.hpmath.academyapi.exception.ErrorCode;
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
