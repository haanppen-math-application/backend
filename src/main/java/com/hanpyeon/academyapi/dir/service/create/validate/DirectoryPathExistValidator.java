package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.exception.DirectoryException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DirectoryPathExistValidator implements DirectoryCreateValidator {
    private final DirectoryRepository directoryRepository;
    @Override
    public void validate(final CreateDirectoryDto createDirectoryDto) {
        directoryRepository.findDirectoryByPath(createDirectoryDto.directoryPath())
                .orElseThrow(() -> new DirectoryException(ErrorCode.NOT_EXIST_DIRECTORY));
    }
}
