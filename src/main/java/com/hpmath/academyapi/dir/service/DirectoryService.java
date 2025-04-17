package com.hpmath.academyapi.dir.service;

import com.hpmath.academyapi.dir.dto.CreateDirectoryCommand;
import com.hpmath.academyapi.dir.dto.DeleteDirectoryDto;
import com.hpmath.academyapi.dir.dto.FileView;
import com.hpmath.academyapi.dir.dto.QueryDirectory;
import com.hpmath.academyapi.dir.dto.UpdateDirectoryDto;
import com.hpmath.academyapi.dir.service.create.DirectoryCreateService;
import com.hpmath.academyapi.dir.service.delete.DirectoryDeleteService;
import com.hpmath.academyapi.dir.service.query.DirectoryQueryService;
import com.hpmath.academyapi.dir.service.update.DirectoryUpdateService;
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
