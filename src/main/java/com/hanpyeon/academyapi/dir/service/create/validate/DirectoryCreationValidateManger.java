package com.hanpyeon.academyapi.dir.service.create.validate;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectoryCreationValidateManger {
    private final List<DirectoryCreateValidator> createValidators;

    @Transactional
    public void validate(final CreateDirectoryDto newDirectoryDto) {
        createValidators.stream().forEach(directoryCreateValidator -> directoryCreateValidator.validate(newDirectoryDto));
    }
}
