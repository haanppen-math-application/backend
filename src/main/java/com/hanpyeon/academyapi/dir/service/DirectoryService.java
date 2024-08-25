package com.hanpyeon.academyapi.dir.service;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.dir.dto.QueryDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryDto;
import com.hanpyeon.academyapi.dir.service.create.DirectoryCreateService;
import com.hanpyeon.academyapi.dir.service.query.DirectoryQueryService;
import com.hanpyeon.academyapi.dir.service.update.DirectoryUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryQueryService directoryQueryService;
    private final DirectoryUpdateService directoryUpdateService;
    private final DirectoryCreateService directoryCreateService;
    @Transactional
    public void addNewDirectory(final CreateDirectoryDto createDirectoryDto) {
        directoryCreateService.addNewDirectory(createDirectoryDto);
    }

    @Transactional(readOnly = true)
    public List<FileView> loadCurrFiles(final QueryDirectoryDto queryDirectoryDto) {
        return directoryQueryService.queryDirectory(queryDirectoryDto);
    }

    @Transactional
    public void updateDirectory(final UpdateDirectoryDto updateDirectoryDto) {
        directoryUpdateService.updateDirectory(updateDirectoryDto);
    }
}
