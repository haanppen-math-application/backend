package com.hanpyeon.academyapi.dir.service;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryCommand;
import com.hanpyeon.academyapi.dir.dto.DeleteDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.dir.dto.QueryDirectory;
import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryDto;
import com.hanpyeon.academyapi.dir.service.create.DirectoryCreateService;
import com.hanpyeon.academyapi.dir.service.delete.DirectoryDeleteService;
import com.hanpyeon.academyapi.dir.service.query.DirectoryQueryService;
import com.hanpyeon.academyapi.dir.service.update.DirectoryUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class DirectoryService {
    private final DirectoryQueryService directoryQueryService;
    private final DirectoryUpdateService directoryUpdateService;
    private final DirectoryCreateService directoryCreateService;
    private final DirectoryDeleteService directoryDeleteService;
    @Transactional
    public void addNewDirectory(@Validated final CreateDirectoryCommand createDirectoryDto) {
        directoryCreateService.addNewDirectory(createDirectoryDto);
    }

    @Transactional(readOnly = true)
    public List<FileView> loadCurrFiles(@Validated final QueryDirectory queryDirectoryDto) {
        return directoryQueryService.queryDirectory(queryDirectoryDto);
    }

    @Transactional
    public void updateDirectory(@Validated final UpdateDirectoryDto updateDirectoryDto) {
        directoryUpdateService.updateDirectory(updateDirectoryDto);
    }

    @Transactional
    public void deleteDirectory(@Validated final DeleteDirectoryDto deleteDirectoryDto) {
        directoryDeleteService.delete(deleteDirectoryDto);
    }
}
