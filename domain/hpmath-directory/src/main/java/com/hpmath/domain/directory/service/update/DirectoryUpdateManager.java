package com.hpmath.domain.directory.service.update;

import com.hpmath.domain.directory.dto.UpdateDirectoryCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DirectoryUpdateManager {
    private final List<DirectoryUpdateHandler> updateHandlers;

    @Transactional
    public void update(final UpdateDirectoryCommand updateDirectoryCommand) {
        updateHandlers.stream()
                .forEach(directoryUpdateHandler -> directoryUpdateHandler.update(updateDirectoryCommand));
    }
}
