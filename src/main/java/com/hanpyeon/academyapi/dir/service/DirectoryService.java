package com.hanpyeon.academyapi.dir.service;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.service.create.DirectoryCreateCommandCreator;
import com.hanpyeon.academyapi.dir.service.create.DirectoryCreator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryCreator directoryCreator;
    private final DirectoryCreateCommandCreator directoryCreateCommandCreator;
    private final DirectoryRepository directoryRepository;

    @Transactional
    public void addNewDirectory(@Valid final CreateDirectoryDto createDirectoryDto) {
        final CreateDirectoryCommand createDirectoryCommand = directoryCreateCommandCreator.create(createDirectoryDto);
        final Directory directory = directoryCreator.createDirectory(createDirectoryCommand);
        directoryRepository.save(directory);
    }
}
