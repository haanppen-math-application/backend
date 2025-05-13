package com.hpmath.domain.board.service.delete.executor;

import com.hpmath.domain.board.dao.Directory;
import java.util.Collection;

interface DeleteDirectoryContentExecutor {

    void delete(final Collection<Directory> directories);
}
