package com.hpmath.domain.board.service.create.validate;

import com.hpmath.domain.board.dao.Directory;
import com.hpmath.domain.board.dao.DirectoryRepository;
import com.hpmath.domain.board.exception.DirectoryException;
import com.hpmath.hpmathcore.ErrorCode;
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
