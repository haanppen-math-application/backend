package com.hpmath.domain.directory.service;

import com.hpmath.domain.directory.dto.UpdateDirectoryCommand;
import com.hpmath.domain.directory.dto.UpdateDirectoryDto;
import com.hpmath.domain.directory.service.update.DirectoryUpdateManager;
import com.hpmath.domain.directory.service.update.UpdateCommandCreator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DirectoryUpdateService {
    private final DirectoryUpdateManager directoryUpdateManager;
    private final UpdateCommandCreator updateCommandCreator;

    public void updateDirectory(@Valid final UpdateDirectoryDto updateDirectoryDto) {
        final UpdateDirectoryCommand updateDirectoryCommand = updateCommandCreator.getUpdateCommand(updateDirectoryDto);
        directoryUpdateManager.update(updateDirectoryCommand);
    }
}
