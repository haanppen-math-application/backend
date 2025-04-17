package com.hpmath.hpmathcoreapi.dir.service.create.validate;

import com.hpmath.hpmathcoreapi.dir.dao.Directory;

interface DirectoryCreateValidator {
    void validate(final Directory directory);
}
