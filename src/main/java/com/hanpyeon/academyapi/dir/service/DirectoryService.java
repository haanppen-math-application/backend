package com.hanpyeon.academyapi.dir.service;

import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.dir.dto.QueryDirectoryDto;
import com.hanpyeon.academyapi.dir.service.create.DirectoryCreateCommandCreator;
import com.hanpyeon.academyapi.dir.service.create.DirectoryCreator;
import com.hanpyeon.academyapi.dir.service.query.DirectoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryCreator directoryCreator;
    private final DirectoryCreateCommandCreator directoryCreateCommandCreator;
    private final DirectoryRepository directoryRepository;
    private final DirectoryQueryService directoryQueryService;

    @Transactional
    public void addNewDirectory(@Validated final CreateDirectoryDto createDirectoryDto) {
        final CreateDirectoryCommand createDirectoryCommand = directoryCreateCommandCreator.create(createDirectoryDto);
        final Directory directory = directoryCreator.createDirectory(createDirectoryCommand);
        directoryRepository.save(directory);
    }

    @Transactional(readOnly = true)
    public List<FileView> loadCurrFiles(final QueryDirectoryDto queryDirectoryDto) {
        return directoryQueryService.queryDirectory(queryDirectoryDto);
    }
}
