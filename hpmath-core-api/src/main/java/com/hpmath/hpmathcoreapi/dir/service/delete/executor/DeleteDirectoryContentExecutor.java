package com.hpmath.hpmathcoreapi.dir.service.delete.executor;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;
import java.util.Collection;

interface DeleteDirectoryContentExecutor {

    void delete(final Collection<Directory> directories);
}
