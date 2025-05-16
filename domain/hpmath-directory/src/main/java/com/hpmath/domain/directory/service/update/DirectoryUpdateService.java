package com.hpmath.domain.directory.service.update;

import com.hpmath.domain.directory.dto.UpdateDirectoryCommand;
import com.hpmath.domain.directory.dto.UpdateDirectoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryUpdateService {
    private final DirectoryUpdateManager directoryUpdateManager;
    private final UpdateCommandCreator updateCommandCreator;

    public void updateDirectory(final UpdateDirectoryDto updateDirectoryDto) {
        final UpdateDirectoryCommand updateDirectoryCommand = updateCommandCreator.getUpdateCommand(updateDirectoryDto);
        directoryUpdateManager.update(updateDirectoryCommand);
    }
}
