package com.hpmath.domain.directory.service.create.validate;

import com.hpmath.domain.directory.dao.Directory;

interface DirectoryCreateValidator {
    void validate(final Directory directory);
}
