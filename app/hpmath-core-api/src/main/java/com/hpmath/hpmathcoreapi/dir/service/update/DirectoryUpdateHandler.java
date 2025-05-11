package com.hpmath.hpmathcoreapi.dir.service.update;

import com.hpmath.hpmathcoreapi.dir.dto.UpdateDirectoryCommand;

interface DirectoryUpdateHandler {
    void update(final UpdateDirectoryCommand updateDirectoryCommand);
}
