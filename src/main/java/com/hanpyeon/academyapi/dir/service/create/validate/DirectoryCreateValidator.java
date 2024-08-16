package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;

interface DirectoryCreateValidator {
    void validate(final CreateDirectoryDto createDirectoryDto);
}
