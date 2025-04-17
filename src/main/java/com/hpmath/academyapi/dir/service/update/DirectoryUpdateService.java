package com.hpmath.academyapi.dir.service.update;

import com.hpmath.academyapi.dir.dto.UpdateDirectoryCommand;
import com.hpmath.academyapi.dir.dto.UpdateDirectoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class DirectoryUpdateService {
    private final DirectoryUpdateManager directoryUpdateManager;
    private final UpdateCommandCreator updateCommandCreator;

    public void updateDirectory(@Validated final UpdateDirectoryDto updateDirectoryDto) {
        final UpdateDirectoryCommand updateDirectoryCommand = updateCommandCreator.getUpdateCommand(updateDirectoryDto);
        directoryUpdateManager.update(updateDirectoryCommand);
    }
}
