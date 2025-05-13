package com.hpmath.domain.board.service.update;

import com.hpmath.domain.board.dto.UpdateDirectoryCommand;

interface DirectoryUpdateHandler {
    void update(final UpdateDirectoryCommand updateDirectoryCommand);
}
