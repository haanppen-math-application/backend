package com.hpmath.domain.board.service.update;

import com.hpmath.domain.board.dto.UpdateDirectoryCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DirectoryUpdateManager {
    private final List<DirectoryUpdateHandler> updateHandlers;

    @Transactional
    public void update(final UpdateDirectoryCommand updateDirectoryCommand) {
        updateHandlers.stream()
                .forEach(directoryUpdateHandler -> directoryUpdateHandler.update(updateDirectoryCommand));
    }
}
